package game.Town.villagers;

import java.util.*;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.villagers.behaviors.*;
import game.Town.villagers.behaviors.gatherer.*;
import game.Town.villagers.behaviors.builder.*;
import game.Town.villagers.behaviors.idle.IdleA;
import processing.core.PVector;

public class Villager {
    private float xPos;
    private float yPos;
    private VILLAGER_ROLES role;
    private boolean idle;
    public ATask btree;
    private double beliefInGod; // 1 is full belief, 0 is no belief.
    protected GodSim g;
    private PVector linear;
    private Deque<RESOURCES> resourcesInHand;
    private int maxResourcesToCarry;
    private RESOURCES resourceToTarget = RESOURCES.NONE;
    private ATile targetTile;
    private Town town;
    private Blackboard blackboard;
    private HashMap<String, Integer> timers = new HashMap<String, Integer>();

    final float moveSpeed = 2;

    public Villager(float xPos, float yPos, VILLAGER_ROLES role) {
        this.town = Town.single();
        this.g = town.getGodSim();
        this.xPos = xPos;
        this.yPos = yPos;
        this.role = role;
        this.idle = true;
        this.beliefInGod = 0.5;
        this.resourcesInHand = new LinkedList<>();
        this.maxResourcesToCarry = 5;
        this.blackboard = Blackboard.single();
        this.setTargetTile(Board.single().getSpawnTile());
        //initializeBTree();
    }

    /*** Getters ***/
    public PVector getPosition() {
        return new PVector(xPos, yPos);
    }
    public VILLAGER_ROLES getRole() {
        return role;
    }
    public Town getTown() {
        return this.town;
    }
    public int getMaxResourcesToCarry() {
        return this.maxResourcesToCarry;
    }
    public ATile getTargetTile() {
        return targetTile;
    }
    public double getBelief() {
        return beliefInGod;
    }
    public RESOURCES getResourceToTarget() {
        return this.resourceToTarget;
    }
    public Deque<RESOURCES> getResourcesInHand() {
        return resourcesInHand;
    }
    public boolean isIdle() {
        return idle;
    }
    public int getTimer(String name) {
        if (timers.get(name) == null) {
            return 0;
        }
        else {
            return timers.get(name);
        }
    }

    /*** Setters ***/
    public void setTargetTile(ATile targetTile) {
        this.targetTile = targetTile;
        calculateLinear();
    }
    public void setResourceToTarget(RESOURCES resource) {
        this.resourceToTarget = resource;
    }
    public void setBelief(double belief) {
        this.beliefInGod = belief;
    }
    public void setVillageRole(VILLAGER_ROLES role) {
        this.role = role;
    }
    public void setBtree(ATask btree) {
        this.btree = btree;
    }
    public void setIdle(boolean val) {
        idle = val;
    }
    public void setTimerNow(String name) { timers.put(name, g.millis()); };
    public void removeTimer(String name) { timers.remove(name); };

    public void draw() {
        PVector dir = PVector.sub(targetTile.getPosition(), getPosition()).setMag(8);
        PVector head = PVector.add(getPosition(), dir);
        g.ellipseMode(g.CENTER);
        g.rectMode(g.CENTER);
        g.imageMode(g.CENTER);
        g.stroke(100,100,100);
        // body
        if (role == VILLAGER_ROLES.GATHERER) {
            g.image(g.gathererB, xPos, yPos);
        } else if (role == VILLAGER_ROLES.BUILDER) {
            g.image(g.builderB, xPos, yPos);
        }
        g.image(g.vHeadImg, head.x, head.y);

        // resource collection text
        g.fill(0);
        g.textAlign(g.CENTER);
        g.text(resourcesInHand.size(), head.x, head.y - 32);
        g.text((float) beliefInGod, head.x, head.y + 50);
    }

    /**
     *  Initialize the Behavior tree based on the current role
     **/
    public void initializeBTree() {
        if (role == VILLAGER_ROLES.GATHERER) {
            setBtree(new Gatherer(this));
        } else if (role == VILLAGER_ROLES.BUILDER) {
            setBtree(new Builder(this));
        }
    }

    /**
     * Execute the villagers behavior tree
     */
    public void act() {
        // check if dying
        if (getTimer("die") != 0) {
            // in the act of dying
            int interval =  new Random().nextInt(500) + 500;
            if (g.millis() - getTimer("die") > interval) {
                ATile markedTile = (ATile) Blackboard.single().get(Integer.toString(hashCode()));
                if (markedTile != null) {
                    markedTile.stopHighlight();
                }
                town.queueVillagerDeath(this);
            }
        }
        // normal behavior
        this.btree.execute();
    }

    public void die() {
        setTimerNow("die");
    }

    /**
     * Simple vector based move toward target
     */
    public void move() {
        this.xPos += linear.x;
        this.yPos += linear.y;
    }

    public void calculateLinear() {
        PVector target = targetTile.getPosition();
        PVector initialLinear;
        if (g.abs(target.x - getPosition().x) > 2
                || g.abs(target.y - getPosition().y) > 2) {
            initialLinear = target.sub(this.getPosition()).normalize();
        } else {
            initialLinear = new PVector(0, 0);
        }
        PVector copyLinear = initialLinear.copy().mult(g.CELL_W / 5);
        PVector futureV = PVector.add(this.getPosition(), copyLinear);
        ATile futureT = Board.single().getTile(futureV);
        initialLinear.mult(moveSpeed);
        linear = initialLinear;
    }

    public void addResource(RESOURCES resource) {
        this.resourcesInHand.add(resource);
    }

    public void changeBelief(double change) {
        this.beliefInGod += change;
        if (this.beliefInGod <= 0) {
            this.beliefInGod = 0.01;
        }
    }
}

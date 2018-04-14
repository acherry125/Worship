package game.Town.villagers;

import java.util.LinkedList;
import java.util.List;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.villagers.behaviors.*;
import game.Town.villagers.behaviors.gatherer.*;
import game.Town.villagers.behaviors.builder.*;
import processing.core.PVector;

public class Villager {
    private float xPos;
    private float yPos;
    private VILLAGER_ROLES role;
    public ATask btree;
    private double beliefInGod; // 1 is full belief, 0 is no belief.
    private PVector target;
    protected GodSim g;
    private PVector linear;
    private List<RESOURCES> resourcesInHand;
    private int maxResourcesToCarry;
    private RESOURCES resourceToTarget = RESOURCES.NONE;
    private boolean onAMission;
    private ATile targetTile;
    private Town town;
    private Blackboard blackboard;

    final float moveSpeed = 3;

    public Villager(float xPos, float yPos, VILLAGER_ROLES role) {
        this.town = Town.single();
        this.g = town.getGodSim();
        this.xPos = xPos;
        this.yPos = yPos;
        this.role = role;
        this.beliefInGod = 0.5;
        this.resourcesInHand = new LinkedList<>();
        this.maxResourcesToCarry = 5;
        this.onAMission = false;
        this.blackboard = Blackboard.single();
        //initializeBTree();
    }

    /*** Getters ***/
    public PVector getTarget() {
        return this.target;
    }
    public PVector getPosition() {
        return new PVector(xPos, yPos);
    }
    public Town getTown() {
        return this.town;
    }
    public int getMaxResourcesToCarry() {
        return this.maxResourcesToCarry;
    }
    public boolean getOnAMission() {
        return onAMission;
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
    public List<RESOURCES> getResourcesInHand() {
        return resourcesInHand;
    }

    /*** Setters ***/
    public void setOnAMission(boolean bool) {
        this.onAMission = bool;
    }
    public void setTargetTile(ATile targetTile) {
        this.targetTile = targetTile;
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
    public void setTarget(PVector target) {
        this.target = target;
        calculateLinear();
    }

    public void draw() {
        PVector curr = new PVector(xPos, yPos);
        curr.add(target.copy().mult(2));
        g.ellipseMode(g.CENTER);
        g.rectMode(g.CENTER);
        g.stroke(100,100,100);
        // body
        if (role == VILLAGER_ROLES.GATHERER) {
            g.fill(254, 176, 80);
        } else if (role == VILLAGER_ROLES.BUILDER) {
            g.fill(254, 80, 80);
        }
        g.ellipse(xPos, yPos, 40, 40);
        g.fill(255, 206, 145);
        // head
        g.ellipse(curr.x, curr.y, 26, 26);
        g.fill(0, 0, 0);
        // hat/hair or whatever
        g.ellipse(curr.x, curr.y, 10, 15);
        // resource collection text bg
        g.fill(255);
        g.stroke(200, 200, 200);
        g.rect(curr.x, curr.y - 40, 70, 20);
        // resource collection text
        g.fill(0);
        g.textAlign(g.CENTER);
        g.text(resourcesInHand.size(), curr.x, curr.y - 32);
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
        this.btree.execute();
    }

    /**
     * Simple vector based move toward target
     */
    public void move() {
        this.xPos += linear.x;
        this.yPos += linear.y;
    }

    public void calculateLinear() {
       PVector initialLinear;
        if (g.abs(target.x - getPosition().x) > 2
                || g.abs(target.y - getPosition().y) > 2) {
            initialLinear = this.target.sub(this.getPosition()).normalize();
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
    }
}

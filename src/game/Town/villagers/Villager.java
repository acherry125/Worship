package game.Town.villagers;

import java.util.LinkedList;
import java.util.List;

import game.Board.ATile;
import game.GodSim;
import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.villagers.behaviors.*;
import game.Town.villagers.behaviors.explorer.*;
import game.Town.villagers.behaviors.builder.*;
import processing.core.PVector;

public class Villager {
    private float xPos;
    private float yPos;
    private VILLAGER_ROLES role;
    public Task btree;
    private double beliefInGod; // 1 is full belief, 0 is no belief.
    private PVector target;
    protected GodSim g;
    private PVector linear;
    private List<RESOURCES> resourcesInHand;
    private int maxResourcesToCarry;
    private RESOURCES resourceToTarget;
    private boolean onAMission;
    private ATile targetTile;
    private Town town;

    final float speed = 3;

    public Villager(Town town, float xPos, float yPos, VILLAGER_ROLES role, GodSim g) {
        this.g = g;
        this.town = town;
        this.xPos = xPos;
        this.yPos = yPos;
        this.role = role;
        this.beliefInGod = 0.5;
        this.resourcesInHand = new LinkedList<>();
        this.maxResourcesToCarry = 5;
        this.onAMission = false;

        //initializeBTree();
    }

    public void setVillageRole(VILLAGER_ROLES role) {
        this.role = role;
        //initializeBTree();
    }

    public void setBtree(Task btree) {
        this.btree = btree;
    }

    public void initializeBTree() {
        if (role == VILLAGER_ROLES.EXPLORER) {
            setBtree(new Explorer(this, town.getTownNeeds(), g.board));
        } else if (role == VILLAGER_ROLES.BUILDER) {
            setBtree(new Builder(this, town.getTownNeeds(), g.board));
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

    public void setTarget(PVector target) {
        this.target = target;
        calculateLinear();
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
        ATile futureT = g.getBoard().getTile(futureV);
        // if the tile that we would end up on is a water tile, avoid it
        /* if (!futureT.isReachable()) {
            PVector tileCenterPush = new PVector(futureT.getXPx() - this.getPosition().x, futureT.getYPx() - this.getPosition().y).normalize().mult(12/10);
            initialLinear.sub(tileCenterPush);
        }*/
        initialLinear.mult(speed);
        linear = initialLinear;
    }

    public PVector getTarget() {
        return this.target;
    }

    public PVector getPosition() {
        return new PVector(xPos, yPos);
    }

    public Town getTown() {
        return this.town;
    }

    public void draw() {

        PVector curr = new PVector(xPos, yPos);
        curr.add(target.copy().mult(2));
        //System.out.println(target.copy().mult(2));

        g.ellipseMode(g.CENTER);
        g.rectMode(g.CENTER);
        g.stroke(100,100,100);
        g.fill(254, 176, 80);
        // body
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
     * Returns the amount of resources the villager is carrying.
     * @return
     */
    public List<RESOURCES> getResourcesInHand() {
        return resourcesInHand;
    }

    /**
     * Returns the max number of resources this villager can carry.
     * @return
     */
    public int getMaxResourcesToCarry() {
        return this.maxResourcesToCarry;
    }

    public void addResource(RESOURCES resource) {
        this.resourcesInHand.add(resource);
    }

    public void setResourceToTarget(RESOURCES resource) {
        this.resourceToTarget = resource;
    }

    public RESOURCES getResourceToTarget() {
        return this.resourceToTarget;
    }

    public void setOnAMission(boolean bool) {
        this.onAMission = bool;
    }

    public boolean isOnAMission() {
        return this.onAMission;
    }

    public void setTargetTile(ATile targetTile) {
        this.targetTile = targetTile;
    }

    public ATile getTargetTile() {
        return this.targetTile;
    }


}

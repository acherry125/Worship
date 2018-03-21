package game.Town;

import game.Board.ATile;
import game.GodSim;
import game.Town.behaviors.Task;
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

    public Villager(GodSim g, float xPos, float yPos, VILLAGER_ROLES role) {
        this.g = g;
        this.xPos = xPos;
        this.yPos = yPos;
        this.role = role;
        this.beliefInGod = 0.5;
    }

    public void setVillageRole(VILLAGER_ROLES role) {
        this.role = role;
    }

    public void setBtree(Task btree) {

        this.btree = btree;
    }

    public void act() {

        this.btree.execute();
    }

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
                && g.abs(target.y - getPosition().y) > 2) {
            initialLinear = this.target.sub(this.getPosition()).normalize();
        } else {
            initialLinear = new PVector(0, 0);
        }
        PVector copyLinear = initialLinear.copy().mult(g.CELL_W / 5);
        PVector futureV = PVector.add(this.getPosition(), copyLinear);
        ATile futureT = g.getBoard().getTile(futureV);
        // if the tile that we would end up on is a water tile, avoid it
        if (!futureT.isReachable()) {
            PVector tileCenterPush = new PVector(futureT.getXPx() - this.getPosition().x, futureT.getYPx() - this.getPosition().y).normalize().mult(12/10);
            initialLinear.sub(tileCenterPush);
        }
        linear = initialLinear;
    }

    public PVector getTarget() {
        return this.target;
    }

    public PVector getPosition() {
        return new PVector(xPos, yPos);
    }

    public void draw() {

        PVector curr = new PVector(xPos, yPos);
        curr.add(target.copy().mult(2));
        g.ellipseMode(g.CENTER);
        g.rectMode(g.CENTER);
        g.fill(254, 176, 80);
        g.ellipse(xPos, yPos, 40, 40);
        g.fill(255, 206, 145);
        g.ellipse(curr.x, curr.y, 26, 26);
        g.fill(0, 0, 0);
        g.ellipse(curr.x, curr.y, 10, 10);

    }
}

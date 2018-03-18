package game.Town;

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
  private VillageNeeds villageNeeds;

  public Villager(GodSim g, float xPos, float yPos, VILLAGER_ROLES role, VillageNeeds villageNeeds) {
    this.g = g;
    this.xPos = xPos;
    this.yPos = yPos;
    this.role = role;
    this.beliefInGod = 0.5;
    this.villageNeeds = villageNeeds;
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
    this.xPos += target.x;
    this.yPos += target.y;
  }

  public void setTarget(float xPos, float yPos) {
    this.target = new PVector(xPos, yPos);
  }

  public void setVector(PVector vector) {
    target = vector;
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
    g.fill(254,176,80);
    g.ellipse(xPos, yPos, 40, 40);
    g.fill(255,206,145);
    g.ellipse(curr.x, curr.y, 26, 26);
    g.fill(0,0,0);
    g.ellipse(curr.x, curr.y, 10, 10);

    move();
  }
}

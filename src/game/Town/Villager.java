package game.Town;

import game.GodSim;
import processing.core.PVector;

public class Villager {
  private float xPos;
  private float yPos;
  private VillagerRoles role;
  public Task btree;
  private double beliefInGod; // 1 is full belief, 0 is no belief.
  private PVector target;
  protected GodSim g;

  public Villager(GodSim g, float xPos, float yPos, VillagerRoles role) {
    this.g = g;
    this.xPos = xPos;
    this.yPos = yPos;
    this.role = role;
    this.beliefInGod = 0.5;
  }

  public void setBtree(Task btree) {
    this.btree = btree;
  }

  public void act() {
    this.btree.execute();
  }

  public void move(float xPos, float yPos) {
    this.xPos += xPos;
    this.yPos += yPos;
  }

  public void setTarget(float xPos, float yPos) {
    this.target = new PVector(xPos, yPos);
  }

  public PVector getTarget() {
    return this.target;
  }

  public PVector getPosition() {
    return new PVector(xPos, yPos);
  }

  public void draw() {
    g.fill(0, 0, 0);
    g.ellipse(xPos, yPos, 20, 20);
  }
}

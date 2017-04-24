package org.adermake.ecoGame2;

import java.awt.Point;

import ch.aplu.jgamegrid.Actor;

public class Spezies extends Actor
{

  // OBJECT VARIABLEN

  // STATS
  private int health;
  private int maxhealth;
  private int strength;
  private int rps;
  private int stamina;
  private double hunger;
  private double maxhunger;
  // BEWEGUNG
  private int x = (int) Math.random() * 791 + 10;
  private int y = (int) Math.random() * 591 + 10;

  private boolean added;

  public Spezies(String skin, int sethealth, int setstrength, int setrps, int setstamina, int sethunger)
  {
    super("sprites/" + skin + ".png");
    hunger = sethunger;
    maxhunger = sethunger;
    health = sethealth;
    maxhealth = sethealth;
    strength = setstrength;

    // COLISION
    setActorCollisionEnabled(true);
    setCollisionCircle(new Point(1, 1), 5);

  }

  @Override
  public int collide(Actor a1, Actor a2)
  {
    super.collide(a1, a2);
    return 10;
  }

  public boolean live()
  {
    if (health <= 0)
      return false;

    if (hunger > maxhunger)
      hunger = maxhunger;

    if (hunger > maxhunger / 2 && health < maxhealth)
    {
      health++;
    }

    if (hunger > 0)
    {
      hunger = hunger - 0.1;

    } else
    {
      health--;
    }
    if (health > 0)
    {
      return true;
    } else
    {
      return false;
    }

  }

  public void die()
  {
    health = 0;
  }

  public void setAdded()
  {
    added = true;
  }

  public boolean isAlive()
  {

    return hunger > 0;
  }

  public boolean isAdded()
  {
    return added;
  }

  public int getHealth()
  {
    return health;
  }

  public int getStrength()
  {
    return strength;
  }

  public int getMaxHealth()
  {
    return maxhealth;
  }

  public double getHunger()
  {
    return hunger;
  }

  public void setStrength(int str)
  {
    strength = str;
  }

  public void setHealth(int hlf)
  {
    strength = hlf;
  }

  public void setHunger(double hgr)
  {
    hunger = hgr;
  }
}
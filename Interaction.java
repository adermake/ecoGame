package org.adermake.ecoGame2;

public class Interaction
{

  public static void fight(Spezies s1, Spezies s2)
  {

    do
    {
      s1.setHealth(s1.getHealth() - s2.getStrength());
      s2.setHealth(s2.getHealth() - s1.getStrength());
    } while (s1.getHealth() > 0 && s2.getHealth() > 0);

    if (s1.getHealth() > 0)
      s1.setHunger((s1.getHunger() + s2.getMaxHealth()));

    if (s2.getHealth() > 0)
      s2.setHunger((s2.getHunger() + s1.getMaxHealth()));

  }
}

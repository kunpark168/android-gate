package com.anhtam.gate9.share;

public enum TabType {
  DISCUSION (0), RATING (1), DATA (2), GAME (3);
  private int tab;

  public int getTab() {
    return tab;
  }

  TabType(int tab) {
    this.tab = tab;
  }

  public static TabType getTab (int tab){
    for (TabType tabType : values()){
      if (tabType.getTab() == tab)
        return tabType;
    }

    return DISCUSION;
  }
}

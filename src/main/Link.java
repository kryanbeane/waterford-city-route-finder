package main;

public class Link {
    public Node<?> src, dest;
    public int cost;
    public boolean easiest;
    public boolean historical;

    public <T> Link(Node<?> src, Node<?> dest, int cost)  {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
    }

    public <T> Link(Node<?> src, Node<?> dest, int cost, boolean hist, boolean easy) {
        this.src = src;
        this.dest = dest;
        this.cost = cost;
        this.historical = hist;
        this.easiest = easy;
    }

}

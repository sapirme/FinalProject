package Graph;

public class Pair<T,S> {
    private T first;
    private S second;

    public Pair (T first, S second){
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "("+first.toString()+","+second.toString()+")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<T,S> other = (Pair<T,S>) o;
        if(this.first.equals(other.getFirst()) && this.second.equals(other.getSecond())) return true;

        else return false;
    }

}
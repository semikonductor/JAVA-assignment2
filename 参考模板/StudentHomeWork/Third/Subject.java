package StudentHomeWork.Third;

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public boolean containObserver(Observer o);
	public void notifyObservers();
}

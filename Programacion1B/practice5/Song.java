package practice5;

public class Song {
	private String title;
	private String release_date;
	private int runtime_minutes;
	private boolean favourite;
	
	public Song(int counter, String title2, String release_date2, int runtime_minutes2, boolean favourite2) {

	}
	
	public Song(String title, String release_date, int runtime_minutes, boolean favourite) {
		this.setTitle(title);
		this.setRelease_date(release_date);
		this.setRuntime_minutes(runtime_minutes);
		this.setFavourite(favourite);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public int getRuntime_minutes() {
		return runtime_minutes;
	}

	public void setRuntime_minutes(int runtime_minutes) {
		this.runtime_minutes = runtime_minutes;
	}

	public boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}
	
	@Override
	public String toString() {
		return "Song title: " + title + ", release_date: " + release_date + ", runtime_minutes: " + runtime_minutes
				+ ", favourite: " + favourite;
	}

}

package practice5;

public class NodeSong {
	NodeSong next;
	Song songs;
	
	//initialize the first element with next=null
	public NodeSong(String title, String release_date, int runtime_minutes, boolean favourite) {
		songs=new Song(title,release_date,runtime_minutes,favourite);
		this.next = null;
    }
	
	public NodeSong(String title, String release_date, int runtime_minutes, boolean favourite, NodeSong next) {
		songs=new Song(title,release_date,runtime_minutes,favourite);
        this.next = next;
    }
	
}

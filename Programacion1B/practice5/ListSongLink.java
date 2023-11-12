package practice5;

public class ListSongLink {
    private NodeSong first;
    private int size;

    public ListSongLink() {
        first = null;
        size = 0;
    }

    public void insert(int i, String title, String release_date, int runtime_time, boolean favourite) {
        if (i == 0 || first==null) {
            first = new NodeSong(title, release_date, runtime_time, favourite, first);
        } else {
            NodeSong aux = first;

            for (int k = 0; k < i - 1; k++) {
                aux = aux.next;
            }
            aux.next = new NodeSong(title, release_date, runtime_time, favourite, aux.next);

        }
        size++;
    }
    public void remove(int i) {
        Song x;
        if (i == 0) {
            x = first.songs;
            first = first.next;
            size--;
            System.out.println("You have deleted the "+x);
            
        }else if(i>0) {
            NodeSong aux = first;
            for (int k = 0; k < i - 1; k++) {
                aux = aux.next;
            }
            // aux is the (i-1)-th node
            x = aux.next.songs;
            aux.next = aux.next.next;
            size--;
            System.out.println("You have deleted the "+x);
        }else if(first==null) {
        	System.out.println("No elements added in the list" );
        }
       
        

    }
    public int size() {
        return size;
    }
    
    public int search(String title){
		NodeSong aux = first;
		int k = 0;
		
		while (aux!=null && !aux.songs.getTitle().equals(title)){ 
			k++;
			aux = aux.next;
		}
		if (aux!=null) {
			return k;
		}else {
			return -1;
		}
    }
    
    public boolean isEmpty() {
        return size == 0;
        // Alternativelly: return first==null;
    }

    public void show() {
        if (first == null) {
            System.out.println("No songs added in the list. Please add some songs.");
        } else {
            while (first != null) {
                System.out.println(first.songs);
                first = first.next;
            }
        }
    }
}
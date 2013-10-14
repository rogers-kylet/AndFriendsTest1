package MenuItem;

public class BasicMenuItem implements MenuItem {

	String text;
	
	public BasicMenuItem(){
		text = "Text";
	}
	
	public BasicMenuItem(String theText){
		this.text = theText;
	}
	
	@Override
	public String getText() {
		return this.text;
	}

}

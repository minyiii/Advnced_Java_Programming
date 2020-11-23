import java.io.Serializable;
import java.util.Date;

public class PostSerializable implements Serializable {
	private String content = "";
	private Date editTime = new Date();
	
	public PostSerializable() {}
	
	public PostSerializable(String content, Date editTime){
		this.content = content;
		this.editTime = editTime;
	}
	
	public void setContent(String newContent){
		this.content = newContent;
	}

	public void setEditTime(Date newEditTime){
		this.editTime = newEditTime;
	}
	
	public String getContent(){
		return this.content;
	}

	public Date getEditTime(){
		return this.editTime;
	}
	

}

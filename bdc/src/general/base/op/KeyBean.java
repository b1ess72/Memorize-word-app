package general.base.op;

public class KeyBean {
	public KeyBean(String content,String hide_content){
		this.content=content;
		this.hide_content=hide_content;
	}
	public String content;
	public String hide_content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHide_content() {
		return hide_content;
	}
	public void setHide_content(String hide_content) {
		this.hide_content = hide_content;
	}
}


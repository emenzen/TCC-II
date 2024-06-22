package model.bo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("soapenv:Body")
public class Body {
	@XStreamAlias("que:post")
	private Mensagem post;

    public Mensagem getsPost() {
        return post;
    }

    public void setPost(Mensagem post) {
        this.post = post;
    }
}

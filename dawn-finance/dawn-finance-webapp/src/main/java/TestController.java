import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class TestController {
	
	public String getTest() {
		return "test";
	}

}

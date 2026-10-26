package validation;

import java.util.ArrayList;
import java.util.List;

public class DeckValidation {
	private List<String> errorMsgList;
	
	public DeckValidation() {
		this.errorMsgList = new ArrayList<>();
	}
	//エラーが1件以上あるか
	public boolean hasErrorMsg() {
		if(errorMsgList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	//必須入力確認
	public void isBlank(String textName, String text) {
		if(text == null || text.isEmpty()) {
			this.errorMsgList.add(textName + "が入力されていません");
		}
	}
	//最小文字数と最大文字数確認
	public void length(String textName, String text, int min, int max) {
		if(text == null || text.length() < min || text.length() > max) {
			this.errorMsgList.add(textName + "は、" + min + "文字以上、" + max + "文字以下で入力してください");
		}
	}
	//最大文字数確認
	public void length(String textName, String text, int max) {
		if(text == null || text.length() > max) {
			this.errorMsgList.add(textName + "は、" + max + "文字以内で入力してください");
		}
	}
	//エラーの追加
	public void addErrorMsg(String msg) {
		errorMsgList.add(msg);
	}
	//エラー一覧
	public List<String> getErrorMsgList(){
		return errorMsgList;
	}
}

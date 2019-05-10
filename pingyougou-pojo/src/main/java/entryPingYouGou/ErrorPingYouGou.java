package entryPingYouGou;

public class ErrorPingYouGou {

	
		private boolean judge;
		private String success;
		
		public ErrorPingYouGou(boolean judge, String success) {
			super();
			this.judge = judge;
			this.success = success;
		}
		public boolean isJudge() {
			return judge;
		}
		public void setJudge(boolean judge) {
			this.judge = judge;
		}
		public String getSuccess() {
			return success;
		}
		public void setSuccess(String success) {
			this.success = success;
		}
		
		
}

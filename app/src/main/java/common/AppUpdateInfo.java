package common;

public class AppUpdateInfo {
	private String VersionName;
	private String ChangeLog;
	private boolean isOptional;
	public boolean isOptional() {
		return isOptional;
	}
	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	public String getVersionName() {
		return VersionName;
	}
	public void setVersionName(String versionName) {
		VersionName = versionName;
	}
	public String getChangeLog() {
		return ChangeLog;
	}
	public void setChangeLog(String changeLog) {
		ChangeLog = changeLog;
	}
	
}

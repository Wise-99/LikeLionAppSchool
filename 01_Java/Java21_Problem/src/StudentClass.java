public class StudentClass {
	
	String name;
	
	int grade;
	int korean;
	int eng;
	int math;
	
	public int total(int s1, int s2, int s3) {
		return s1 + s2 + s3;
	}
	
	
	public int avg(int s1, int s2, int s3) {
		return (s1 + s2 + s3) / 3;
	}
}


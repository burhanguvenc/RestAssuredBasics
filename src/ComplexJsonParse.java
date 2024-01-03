import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(Payload.coursePrice());
		
		//1. Print No of courses returned by API
		System.out.println("Print No of courses returned by API");
		
		int count = js.getInt("courses.size()");
		System.out.println(count);

		//2.Print Purchase Amount
		System.out.println("Print Purchase Amount");
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		//3. Print Title of the first course
		System.out.println("Print Title of the first course");
		
		String firstTitle = js.get("courses[2].title");
		System.out.println(firstTitle);

		//4. Print All course titles and their respective Prices
		System.out.println("Print All course titles and their respective Prices");
		
		for(int i=0; i<count;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			String p = js.get("courses["+i+"].price").toString();
			
			System.out.println(p);
			System.out.println(courseTitles);
		}

		//5. Print no of copies sold by RPA Course
		System.out.println("Print no of copies sold by RPA Course");
		
		for(int i=0; i<count;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA"))
			{
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
	}

}

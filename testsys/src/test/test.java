package test;

public class test{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("1231321");
		System.out.println(divR(100));//
		//quick & sample
		//test new branch dev
		//git stash list1
	}

	public static int divR(int a){
		if(a==1)
			return a;
		else
			return a+divR(a-1);
	}
}

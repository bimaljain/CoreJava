/*
 Lambda Expression Scope
 A lambda expression does not define a scope of its own.

If we use keywords this and super in lambda expression inside a method, they act the same as we use them in that method.

*/

package _010_lambda;

import java.util.function.Function;

public class _027_Scope {
	String s0="meghna";
	public static void main(String[] args){
		_027_Scope obj = new _027_Scope();
		obj.fn1(); obj.fn2(); obj.fn3(); obj.fn4(); obj.fn5(); obj.fn6();
	}

	//The first line in the Main method has a variable definition for x.
	//If we remove the comment we would get compile time error since it is conflicting with the variable definition of the lambda expression.
	//This is showing that the lambda expression has the same scope with its outside method. And the lambda expression doesn't create its own scope.
	
	//The following code outputs the this from a lambda expression. this in the lambda expression refers to the outside class not the lambda expression itself.
	public void fn1(){
		//int x= 0;
		Function<String,String> func1 = x -> {System.out.println(this);return x ;};
		func1.apply("");
	}
	
	/*
	 * A lambda expression can access final local variables or local-non-final-initialized-only-once variables.
	 */
	//The following code shows that we can access and use the final local variables.
	public void fn2(){
		final String s="bimal";
		Function<String, String> s2 = x->x+s;
		System.out.println(s2.apply("jain "));
	}
	
	//The following code has a variable x which is not final but only initialized once. We can still use it in the lambda expression
	public void fn3(){
		String s="bimal";
		Function<String, String> s2 = x->x+s;
		System.out.println(s2.apply("jain "));
	}
	
	//The following code shows that we cannot change the value defined outside lambda expression.
	public void fn4(){
		String s="bimal";
		Function<String, String> s2 = x-> {/*s="bharat";*/ return x+s;};
		System.out.println(s2.apply("jain "));
	}
	
	public void fn5(){
		final String s="bimal";
		Function<String, String> s2 = x-> {/*s="bharat";*/ return x+s;};
		System.out.println(s2.apply("jain "));
	}
	
	//We can change the non-local variable in lambda expression.
	public void fn6(){
		final String s="bimal";
		Function<String, String> s2 = x-> {s0="bharat"; return x+s;};
		System.out.println(s2.apply("jain "));
	}
	
}

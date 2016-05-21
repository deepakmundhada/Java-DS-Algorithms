/* Problem Statement: Create Tautology Verifier which can have maximum 26 variables and operation
&, |, ! only.

Input -
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

//Binary Tree node structure
class Node {
	char val;
	Node right, left;
}

public class TautologyVerifier {
	static List<Boolean> isval = new ArrayList<Boolean>();
	static List<Character> value = new ArrayList<Character>();
	static boolean isInsert = false;
	static boolean isTautology = true;
	static Node root ;

	//Add node to the tree
	static Node insertNode(Node head, char c) {
		if(isInsert) return head;

		if(head == null) {
			head = new Node();
			head.val = c;
			head.right= null;
			head.left = null;

			isInsert = true;

			if(root == null) root = head;

			return head;
		}

		if(Character.isLowerCase(head.val)) {
			return head;
		}
		else if(head.val == '!') {
			head.left = insertNode(head.left, c);
		}
		else {
			head.left = insertNode(head.left, c);
			head.right = insertNode(head.right, c);
		}

		return head;
	}

	//Check if expression is tautology or not
	static boolean eval(Node head) {
		if(head != null && Character.isLowerCase(head.val)) {
			return isval.get(head.val - 'a');
		}

		char op = head.val;
		boolean tr1, tr2 = false;

		tr1 = eval(head.left) ;

		if(op != '!') {
			tr2 = eval(head.right);
		}

		switch (op) {
			case '&': return tr1 && tr2;
			case '|': return tr1 || tr2;
			case '!': return !tr1;
			default : return false;
		}
	}

	static void search(int pos, int len) {
		if(!isTautology) {
			return;
		}
		else if(pos == len) {
			Node head = root;
			if(!eval(head)) isTautology = false;
			return;
		}

		search(pos+1, len);
		isval.add(value.get(pos) - 'a', false);
		search(pos+1, len);
		isval.add(value.get(pos) -'a', true);
	}

	static boolean isOperator(char element) {
	    if(element == '&' || element == '|' || element == '!') {
	        return true;
	    }
        return false;
	}

	//Convert expression from Infix to prefix
	static String convertInfixToPrefix(String str) {
		Stack<Character> stack = new Stack<Character>();
        String prefix = "";
        str = str.replaceAll("\\s+","");

        try {
	        for(int i = str.length()-1; i >= 0; i--) {
	            char c = str.charAt(i);

	            if(Character.isLetter(c) || c == '!') {
	                prefix = ((Character)c).toString() + prefix;
	            }
	            else if(c == '(') {
	            	while(!stack.isEmpty()) {
	            		prefix = ((Character)stack.pop()).toString() + prefix;
	            	}
	            }
	            else if(c == ')') {
	            	continue;
	            }
	            else if(isOperator(c)) {
	            	stack.push(c);
	            }
	            else {
	            	throw new Exception();
	            }
	        }

	        while(!stack.isEmpty()) {
	            prefix = ((Character)stack.pop()).toString() + prefix;
	        }
	    } catch(Exception e) {
	    	System.out.println("expression is not valid");
	    }

	    System.out.println("prefix " + prefix);
        return prefix;
    }

	static void IsExpressionTautology(String expression) throws Exception {
		root = null;
		isTautology = true;

		char[] exprInPrefix = convertInfixToPrefix(expression).toCharArray();

		Node head = null;
		int tempLen = exprInPrefix.length;

		for(int i=0; i< tempLen; i++) {
			if(Character.isLowerCase(exprInPrefix[i]) && !isval.get(exprInPrefix[i] - 'a')) {
				isval.add(exprInPrefix[i] -'a', true);
				value.add(exprInPrefix[i]);
			}

			head = insertNode(head, exprInPrefix[i]);
			isInsert = false;
		}

		search(0, value.size());

		if(isTautology) {
			System.out.println("True");
		}
		else {
			System.out.println("False");
		}

		value.clear();
	}

	public static void main(String args []) throws Exception {
		Scanner sc = new Scanner(System.in);

		for(int i = 0; i < 26; i++) {
			isval.add(false);
		}

		while(sc.hasNext()) {
			IsExpressionTautology(sc.nextLine());
		}
	}
}


/*6
(!a | a)
(!a | (a & a))
(!a | (b & !a))
(!a & !b & (c & d))
(a & (b | c))
((a & (!b | b)) | (!a & (!b | b)))*/

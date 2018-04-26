package triple;

import triple.Solution.Node;

public interface IVistor<T,K> {

	void apply(Node<K> node);
	
	T result();

}

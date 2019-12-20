/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 13523
 */
import java.util.List;

public class RedBlackTreeimp {

	private final int RED = 0;
	private final int BLACK = 1;

	public class Node {

		int key = -1, color = BLACK;
		Node left = nil, right = nil, parent = nil;
		Building building = null;

		Node(int key) {
			this.key = key;
		}

		Node(int key, Building building) {
			this.key = key;
			this.building = building;
		}
	}

	public final Node nil = new Node(-1, null);
	public Node root = nil;

	

	private Node search(Node search, Node node) {
		if (root == nil) {
			return null;
		}

		if (search.key < node.key) {
			if (node.left != nil) {
				return search(search, node.left);
			}
		} else if (search.key > node.key) {
			if (node.right != nil) {
				return search(search, node.right);
			}
		} else if (search.key == node.key) {
			return node;
		}
		return null;
	}

	private void insert(Node node) {
		Node temp = root;
		if (root == nil) {
			root = node;
			node.color = BLACK;
			node.parent = nil;
		} else {
			node.color = RED;
			while (true) {
				if (node.key < temp.key) {
					if (temp.left == nil) {
						temp.left = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if (node.key >= temp.key) {
					if (temp.right == nil) {
						temp.right = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			repair(node);
		}
	}

	// Takes as argument the newly inserted node
	private void repair(Node node) {
		while (node.parent.color == RED) {
			Node uncle = nil;
			if (node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.right) {
					// Double rotation needed
					node = node.parent;
					rotateLeft(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				rotateRight(node.parent.parent);
			} else {
				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {
					// Double rotation needed
					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				rotateLeft(node.parent.parent);
			}
		}
		root.color = BLACK;
	}

	void rotateLeft(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {// Need to rotate root
			Node right = root.right;
			root.right = right.left;
			right.left.parent = root;
			root.parent = right;
			right.left = root;
			right.parent = nil;
			root = right;
		}
	}

	void rotateRight(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {// Need to rotate root
			Node left = root.left;
			root.left = root.left.right;
			left.right.parent = root;
			root.parent = left;
			left.right = root;
			left.parent = nil;
			root = left;
		}
	}

	// Deletes whole tree
	void deleteTree() {
		root = nil;
	}

	// Deletion Code .

	// This operation doesn't care about the new Node's connections
	// with previous node's left and right. The caller has to take care
	// of that.
	void translate(Node target, Node with) {
		if (target.parent == nil) {
			root = with;
		} else if (target == target.parent.left) {
			target.parent.left = with;
		} else
			target.parent.right = with;
		with.parent = target.parent;
	}

	public boolean delete(Node z) {
		if ((z = search(z, root)) == null)
			return false;
		Node p;
		Node q = z; // temporary reference q
		int q_color = q.color;

		if (z.left == nil) {
			p = z.right;
			translate(z, z.right);
		} else if (z.right == nil) {
			p = z.left;
			translate(z, z.left);
		} else {
			q = treeMinimum(z.right);
			q_color = q.color;
			p = q.right;
			if (q.parent == z)
				p.parent = q;
			else {
				translate(q, q.right);
				q.right = z.right;
				q.right.parent = q;
			}
			translate(z, q);
			q.left = z.left;
			q.left.parent = q;
			q.color = z.color;
		}
		if (q_color == BLACK)
			repairDelete(p);
		return true;
	}

	void repairDelete(Node p) {
		while (p != root && p.color == BLACK) {
			if (p == p.parent.left) {
				Node w = p.parent.right;
				if (w.color == RED) {
					w.color = BLACK;
					p.parent.color = RED;
					rotateLeft(p.parent);
					w = p.parent.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					p = p.parent;
					continue;
				} else if (w.right.color == BLACK) {
					w.left.color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = p.parent.right;
				}
				if (w.right.color == RED) {
					w.color = p.parent.color;
					p.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(p.parent);
					p = root;
				}
			} else {
				Node w = p.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					p.parent.color = RED;
					rotateRight(p.parent);
					w = p.parent.left;
				}
				if (w.right.color == BLACK && w.left.color == BLACK) {
					w.color = RED;
					p = p.parent;
					continue;
				} else if (w.left.color == BLACK) {
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = p.parent.left;
				}
				if (w.left.color == RED) {
					w.color = p.parent.color;
					p.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(p.parent);
					p = root;
				}
			}
		}
		p.color = BLACK;
	}

	Node treeMinimum(Node subTreeRoot) {
		while (subTreeRoot.left != nil) {
			subTreeRoot = subTreeRoot.left;
		}
		return subTreeRoot;
	}

	public Node search(Node node) {
		return search(node, root);
	}

	public Building findBuilding(int buildingId) {
		if (search(new Node(buildingId)) == null) {
			return null;
		}
		return search(new Node(buildingId)).building;
	}
	
	public void findBuildingsBetweenRange(Node root, int buildingId1, int buildingId2, List<Building> result) {
		if(root == nil) return;
		
		if(root.building.buildingId - buildingId1 >= 0 && buildingId2 - root.building.buildingId >=0) {
			findBuildingsBetweenRange(root.left, buildingId1, buildingId2, result);
			result.add(root.building);
			findBuildingsBetweenRange(root.right, buildingId1, buildingId2, result);
		} else if(root.building.buildingId - buildingId1 < 0) {
			findBuildingsBetweenRange(root.right, buildingId1, buildingId2, result);
		} else {
			findBuildingsBetweenRange(root.left, buildingId1, buildingId2, result);
		}
	}

	public void insertBuilding(Building j) {
		insert(new Node(j.getBuildingId(), j));
	}

	public boolean removeBuilding(int buildingId) {
		return delete(new Node(buildingId));
	}

}
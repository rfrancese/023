package com.apgb.theline;



public class ArrayIndexList<E> implements IndexList<E> {
	private E[] A;
	private static final int CAPACITY=1024;
	private int capacity;
	private int size=0;
	
	public ArrayIndexList(int cap){
		A = (E[]) new Object[cap];
		capacity=cap;
	}
	public ArrayIndexList(){
		this(CAPACITY);
	}

	@Override
	public E remove(int r) throws IndexOutOfBoundsException {
		checkIndex(r, size());
		E temp = A[r];
		for (int i=r; i<size-1; i++)
			A[i] = A[i+1];
		size--;
		return temp;
	}

	@Override
	public void add(int r, E e) throws IndexOutOfBoundsException {
		checkIndex(r, size() + 1);
		if(size== capacity){
			capacity *=2;
			E[] B = (E[]) new Object[capacity];
			for (int i=0; i<size; i++)
				B[i] = A[i];
			A=B;
		}
		for(int i=size-1; i>=r; i--)
			A[i+1]=A[i];
		A[r]=e;
		size++;
	}

	@Override
	public E set(int r, E e) throws IndexOutOfBoundsException {
			checkIndex(r, size());
			E temp = A [r];
			A[r] = e;
			return temp;
	}

	@Override
	public E get(int r) throws IndexOutOfBoundsException {
			checkIndex(r, size());
			return A[r];
		
	}

	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public int size() {
		return size;
	}
	
	protected void checkIndex(int r, int n) throws IndexOutOfBoundsException {
		if(r<0 || r>=n)
			throw new IndexOutOfBoundsException("Indice non presente");
	}

}

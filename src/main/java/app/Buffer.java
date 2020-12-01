package app;

import java.util.ArrayList;


public class Buffer {
    private int size;
    private int pointer;
    private ArrayList<Request> requests;


    public Buffer(int size) {
        this.size = size;
        pointer = 0;
        requests = new ArrayList<>(size);
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    private boolean findFreePlaceInBuffer(Request request, double currTime, int staticPointer) {
        for (int i = pointer; i < size; i++) {
            if (requests.get(i) == null) {
                requests.set(i, request);
                request.setTimeInBuffer(currTime);
                incrementPointer();
                return true;
            }
            incrementPointer();
        }
        for (int i = 0; i < staticPointer; i++) {
            if (requests.get(i) == null) {
                requests.set(i, request);
                request.setTimeInBuffer(currTime);
                incrementPointer();
                return true;
            }
            incrementPointer();
        }
        return false;
    }

    public Pair<Integer, Integer>  put(Request request, double currTime) {
        if (requests.size() < size) {
            requests.add(pointer, request);
            incrementPointer();
            return new Pair<>(0, -1);
        } else {
            int staticPointer = pointer;
            if (findFreePlaceInBuffer(request, currTime, staticPointer)) {
                return new Pair<>(0, -1);
            }
            else {
                int  rejectedSourceNumber = removeOldestRequest();
                pointer = staticPointer;
                if (findFreePlaceInBuffer(request, currTime, staticPointer)) {
                    return new Pair<>(1, rejectedSourceNumber);
                }
            }

        }
        return new Pair<>(2, request.getSourceNumber());
    }

    private int removeOldestRequest(){
        int currIndex = 0;
        for (int i = 1; i < size; i++) {
            if (requests.get(i).getTimeInBuffer() < requests.get(currIndex).getTimeInBuffer()) {
                currIndex = i;
            }
        }
        int sourceNumber = requests.get(currIndex).getSourceNumber();
        requests.set(currIndex, null);
        return sourceNumber;
}

    public Request getLatestRequest(){
        if (isEmpty()){
            System.out.println("empty");

        }
        else {
            int currIndex = 0;
            for (int i = 1; i < requests.size(); i++) {
                if (requests.get(i) != null){
                    currIndex = i;
                    break;
                }
            }

            for (int i = 1; i < requests.size(); i++) {
                if (requests.get(i) != null) {
                    if (requests.get(i).getTimeInBuffer() > requests.get(currIndex).getTimeInBuffer()) {
                        currIndex = i;
                    }
                }
            }
            Request last = requests.get(currIndex);
            requests.set(currIndex, null);
            System.out.println(currIndex);
            return last;
        }

        return null;
    }

    private boolean isFull() {
        return size == requests.size();
    }

    public boolean isEmpty() {
        return requests.isEmpty();
    }

    private void incrementPointer() {
        pointer++;
        if (pointer == size) {
            pointer = 0;
        }
    }
    public int getIndexPointer() {
        return pointer;
    }


}

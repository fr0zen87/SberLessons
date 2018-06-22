package com.example.lesson4;

public class StateManager {
    private static final StateManager ourInstance = new StateManager();

    public static StateManager getInstance() {
        return ourInstance;
    }

    private StateManager() {
    }

    private State state = State.A;

    private enum State {
        A,
        B,
        C,
        D,
        E
    }

    public String getState() {
        return String.valueOf(state);
    }

    public void changeState() {
        switch (state) {
            case A: {
                state = State.B;
                break;
            }
            case B: {
                state = State.C;
                break;
            }
            case C: {
                state = State.D;
                break;
            }
            case D: {
                state = State.E;
                break;
            }
            case E: {
                state = State.A;
                break;
            }
        }
    }
}

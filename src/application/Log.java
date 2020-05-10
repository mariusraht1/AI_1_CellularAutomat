package application;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

public class Log {
    private static Log instance;

    public static Log getInstance() {
	if (instance == null) {
	    instance = new Log();
	}

	return instance;
    }

    private Log() {
    }

    private ArrayList<String> buffer = new ArrayList<String>();

    private TextArea control;

    public void setOutputControl(TextArea control) {
	this.control = control;
	clear();
    }

    public void add(String message) {
	System.out.println(message);

	if (control != null) {
	    if (buffer.size() > 0) {
		for (String msg : buffer) {
		    control.appendText(msg + "\n");
		}

		buffer.clear();
	    }

	    control.appendText(message + "\n");
	} else {
	    buffer.add(message);
	}
    }

    public void clear() {
	buffer.clear();
	control.clear();
    }
}

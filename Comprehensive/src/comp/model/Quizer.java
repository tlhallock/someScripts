package comp.model;

import comp.gui.Controller;
import comp.gui.Prompter;
import comp.gui.Solution;

public class Quizer
{
    QuizerState state;
    
    Controller controller;
    Prompter prompter;
    Solution solutions;
    
    
    
    

    public Quizer(Controller controller, Prompter prompter,
			Solution solutions) {
		this.state = QuizerState.Question;
		
		this.controller = controller;
		this.prompter = prompter;
		this.solutions = solutions;
	}





    enum QuizerState
    {
        Question,
        Show,
    }
}

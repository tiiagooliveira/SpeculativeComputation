package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */
public class Formula extends ClinicalAction {
    private ArrayList<Parameter> parameters;
    private Parameter result;
    private String mathematicalExpression;

    public Formula(){
        parameters = new ArrayList<Parameter>();
    }

    public static Formula fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Formula.class);
    }

    @Override
	public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Parameter getResult() {
        return result;
    }

    public void setResult(Parameter result) {
        this.result = result;
    }

    public String getMathematicalExpression() {
        return mathematicalExpression;
    }

    public void setMathematicalExpression(String mathematicalExpression) {
        this.mathematicalExpression = mathematicalExpression;
    }
}

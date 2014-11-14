package cguide.execution.entities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 30-08-2013
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */

public  class ClinicalActionAdapter implements JsonSerializer<ClinicalAction>, JsonDeserializer<ClinicalAction> {

    @Override
	public JsonElement serialize(ClinicalAction clinicalAction, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        Type typeParameter = new TypeToken<Collection<Parameter>>(){}.getType();
        result.add("id", context.serialize(clinicalAction.getId(), String.class));
        result.add("name", context.serialize(clinicalAction.getName(), String.class));
        result.add("actionType", context.serialize(clinicalAction.getActionType(), String.class));
        result.add("description", context.serialize(clinicalAction.getDescription(), String.class));

        if (clinicalAction instanceof MedicationRecommendation) {
            result.add("activeIngredient", context.serialize(((MedicationRecommendation) clinicalAction).getActiveIngredient(), String.class));
            result.add("dosage", context.serialize(((MedicationRecommendation) clinicalAction).getActiveIngredient(), String.class));
            result.add("pharmaceuticalForm", context.serialize(((MedicationRecommendation) clinicalAction).getPharmaceuticalForm(), String.class));
            result.add("posology", context.serialize(((MedicationRecommendation) clinicalAction).getPosology(), String.class));

        }
        if (clinicalAction instanceof Formula) {
            result.add("parameters", context.serialize(((Formula) clinicalAction).getParameters(), typeParameter));
            result.add("result", context.serialize(((Formula) clinicalAction).getResult(), Parameter.class));
            result.add("duration", context.serialize(((Formula) clinicalAction).getMathematicalExpression(), String.class));
        }

        return result;
    }

    @Override
	public ClinicalAction deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        ClinicalAction result = null;


        Type typeParameter = new TypeToken<Collection<Parameter>>(){}.getType();


        // if the employee has an assistant, she must be the CEO
        JsonElement parameters = object.get("parameters");
        JsonElement mathematicalExpression = object.get("mathematicalExpression");
        JsonElement resultParameter = object.get("result");
        if (parameters != null) {
            result = new Formula();

            ((Formula) result).setParameters((ArrayList<Parameter>) context.deserialize(parameters, typeParameter));
            ((Formula) result).setResult((Parameter) context.deserialize(resultParameter, Parameter.class));
            ((Formula) result).setMathematicalExpression((String) context.deserialize(mathematicalExpression, String.class));
          }

        JsonElement activeIngredient = object.get("activeIngredient");
        JsonElement dosage = object.get("dosage");
        JsonElement pharmaceuticalForm = object.get("pharmaceuticalForm");
        JsonElement posology = object.get("posology");
        if (activeIngredient != null) {
            result = new MedicationRecommendation();
            ((MedicationRecommendation) result).setActiveIngredient((String) context.deserialize(activeIngredient, String.class));
            ((MedicationRecommendation) result).setDosage((String) context.deserialize(dosage, String.class));
            ((MedicationRecommendation) result).setPharmaceuticalForm((String) context.deserialize(pharmaceuticalForm, String.class));
            ((MedicationRecommendation) result).setPosology((String) context.deserialize(posology, String.class));
        }



        if (result == null) {
            result = new ClinicalAction();
        }
        result.setId((String)context.deserialize(object.get("id"), String.class));
        result.setActionType((String)context.deserialize(object.get("actionType"), String.class));
        result.setDescription((String)context.deserialize(object.get("description"), String.class));
        result.setName((String)context.deserialize(object.get("name"), String.class));
        return result;
    }
}
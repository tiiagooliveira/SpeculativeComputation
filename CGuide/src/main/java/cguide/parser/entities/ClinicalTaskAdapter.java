package cguide.parser.entities;

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

public  class ClinicalTaskAdapter implements JsonSerializer<ClinicalTask>, JsonDeserializer<ClinicalTask> {

    @Override
	public JsonElement serialize(ClinicalTask clinicalTask, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        Type typeString = new TypeToken<Collection<String>>(){}.getType();
        Type typeParameter = new TypeToken<Collection<Parameter>>(){}.getType();
        Type typeOptions = new TypeToken<Collection<Option>>(){}.getType();
        Type typeClinicalAction = new TypeToken<Collection<ClinicalAction>>(){}.getType();
        Type typeOutcome = new TypeToken<Collection<Outcome>>(){}.getType();
        Type typeTriggerCondition = new TypeToken<Collection<TriggerCondition>>(){}.getType();
        Type typePreCondition = new TypeToken<Collection<PreCondition>>(){}.getType();

        result.add("id", context.serialize(clinicalTask.getId(), String.class));
        result.add("clinicalTasks", context.serialize(clinicalTask.getClinicalTasks(), typeString));
        result.add("taskType", context.serialize(clinicalTask.getTaskType(), String.class));
        result.add("taskFormat", context.serialize(clinicalTask.getTaskFormat(), String.class));
        result.add("syncTask", context.serialize(clinicalTask.getSyncTask(), String.class));
        result.add("generalDescription", context.serialize(clinicalTask.getGeneralDescription(), String.class));
        result.add("triggerCondition", context.serialize(clinicalTask.getTriggerCondition(), typeTriggerCondition));
        result.add("preCondition", context.serialize(clinicalTask.getPreCondition(), typePreCondition));


        if (clinicalTask instanceof Question) {
            result.add("parameters", context.serialize(((Question) clinicalTask).getParameters(), typeParameter));
        }
        if (clinicalTask instanceof Plan) {
            result.add("outcome", context.serialize(((Plan) clinicalTask).getOutcome(), typeOutcome));
            result.add("firstClinicalTask", context.serialize(((Plan) clinicalTask).getFirstClinicalTask(), typeString));
            result.add("firstTaskFormat", context.serialize(((Plan) clinicalTask).getFirstTaskFormat(), String.class));
            result.add("duration", context.serialize(((Plan) clinicalTask).getDuration(), Duration.class));
            result.add("pedriodicity", context.serialize(((Plan) clinicalTask).getPeriodicity(), Periodicity.class));

        }
        if (clinicalTask instanceof Decision) {
            result.add("options", context.serialize(((Decision) clinicalTask).getOptions(), typeOptions));
        }


        if (clinicalTask instanceof Action) {
            result.add("clinicalActions", context.serialize(((Action) clinicalTask).getClinicalActions(), typeClinicalAction));
            result.add("outcome", context.serialize(((Action) clinicalTask).getOutcome(), Outcome.class));
            result.add("duration", context.serialize(((Action) clinicalTask).getDuration(), Duration.class));
            result.add("periodicity", context.serialize(((Action) clinicalTask).getPeriodicity(), Periodicity.class));
        }

        return result;
    }

    @Override
	public ClinicalTask deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        ClinicalTask result = null;



        Type typeString = new TypeToken<Collection<String>>(){}.getType();
        Type typeParameter = new TypeToken<Collection<Parameter>>(){}.getType();
        Type typeOptions = new TypeToken<Collection<Option>>(){}.getType();
        Type typeClinicalAction = new TypeToken<Collection<ClinicalAction>>(){}.getType();
        Type typeOutcome = new TypeToken<Collection<Outcome>>(){}.getType();
        Type typeTriggerCondition = new TypeToken<Collection<TriggerCondition>>(){}.getType();
        Type typePreCondition = new TypeToken<Collection<PreCondition>>(){}.getType();


        // if the employee has an assistant, she must be the CEO
        JsonElement parameter = object.get("parameters");
        if (parameter != null) {
            result = new Question();

            ((Question) result).setParameters((ArrayList<Parameter>) context.deserialize(parameter, typeParameter));
         }

        JsonElement option = object.get("options");
        if (option != null) {
            result = new Decision();
            ((Decision) result).setOptions((ArrayList<Option>) context.deserialize(option,typeOptions));
        }

        JsonElement firstClinicTask = object.get("firstClinicTask");
        JsonElement outcome = object.get("outcome");
        JsonElement firstTaskFormat = object.get("firstTaskFormat");
        JsonElement duration = object.get("duration");
        JsonElement periodicity = object.get("periodicity");
        if (firstClinicTask != null) {
            result = new Plan();

            ((Plan) result).setFirstClinicalTask((ArrayList<String>) context.deserialize(firstClinicTask, typeString));
            ((Plan) result).setOutcome((ArrayList<Outcome>) context.deserialize(outcome, typeOutcome));
            ((Plan) result).setFirstTaskFormat((String) context.deserialize(firstTaskFormat, String.class));
            ((Plan) result).setDuration((Duration) context.deserialize(duration, Duration.class));
            ((Plan) result).setPeriodicity((Periodicity) context.deserialize(periodicity, Periodicity.class));
        }

        JsonElement clinicalActions = object.get("clinicalActions");
        outcome= object.get("outcome");
        duration = object.get("duration");
        periodicity = object.get("periodicity");
        if (clinicalActions != null) {
            result = new Action();
            ((Action) result).setClinicalActions((ArrayList<ClinicalAction>) context.deserialize(clinicalActions, typeClinicalAction));
            ((Action) result).setOutcome((Outcome) context.deserialize(outcome, Outcome.class));
            ((Action) result).setDuration((Duration) context.deserialize(duration, Duration.class));
            ((Action) result).setPeriodicity((Periodicity) context.deserialize(periodicity, Periodicity.class));
        }

        if (result == null) {
            result = new ClinicalTask();
        }
        result.setId((String)context.deserialize(object.get("id"), String.class));
        result.setClinicalTasks((ArrayList<String>) context.deserialize(object.get("clinicalTasks"), typeString));
        result.setTaskType((String) context.deserialize(object.get("taskType"), String.class));
        result.setTaskFormat((String) context.deserialize(object.get("taskFormat"), String.class));
        result.setSyncTask((String) context.deserialize(object.get("syncTask"), String.class));
        result.setGeneralDescription((String) context.deserialize(object.get("generalDescription"), String.class));
        result.setTriggerCondition((ArrayList<TriggerCondition>) context.deserialize(object.get("triggerCondition"), typeTriggerCondition));
        result.setPreCondition((ArrayList<PreCondition>) context.deserialize(object.get("preCondition"), typePreCondition));

        return result;
    }
}
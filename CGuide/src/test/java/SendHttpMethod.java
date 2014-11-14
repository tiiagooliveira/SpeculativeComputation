import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.naming.Context;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import cguide.db.entities.GuidelineList;
import cguide.db.entities.User;
import cguide.execution.ProcessedTask;
import cguide.execution.entities.Action;
import cguide.execution.entities.ClinicalAction;
import cguide.execution.entities.ClinicalActionAdapter;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.ClinicalTaskAdapter;
import cguide.execution.entities.Decision;
import cguide.execution.entities.End;
import cguide.execution.entities.Formula;
import cguide.execution.entities.Plan;
import cguide.execution.entities.Question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class SendHttpMethod {

    final public static String INVALID_URL = "INVALID_URL";
    final public static String IO_EXCEPTION = "IO_EXCEPTION";
    final public static String CLI_EXCEPTION = "CLI_EXCEPTION";

	private String URL;
	private Integer HttpCode;
	private Header cookieLogin;
	private Header cookieValidade;
	private Header[] headers;
	private ArrayList<BasicNameValuePair> valuePairs;
	private Context context;
	
//	private static final String TAG = "HttpClient";
			
		public SendHttpMethod(String url){
			this.URL = url;
			cookieLogin=null;
			cookieValidade=null;
			HttpCode=-1;
			valuePairs=null;
			
		}
		
	

		public SendHttpMethod(String url, ArrayList<BasicNameValuePair> valuePairs){
			this.URL = url;
			cookieLogin=null;
			cookieValidade=null;
			HttpCode=-1;
			this.valuePairs=valuePairs;
			
		}
		
		public SendHttpMethod(String url, ArrayList<BasicNameValuePair> valuePairs, Context c){
			this.URL = url;
			cookieLogin=null;
			cookieValidade=null;
			HttpCode=-1;
			this.valuePairs=valuePairs;
			this.context=c;
			
		}
		
		
		
		public static String convertStreamToString(InputStream is) {





			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
		
		public String sendUpdateUserDetails(Header h){
			
			if(URL==null) return "URL invalido";
			
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPostRequest = new HttpPost(URL);
			
			if(h==null) return "Token: Sem permissao para efectuar o pedido";
			httpPostRequest.addHeader("cookie", h.getValue());
			
			try{			
			    UrlEncodedFormEntity urlEncodedFormEntity;
				urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
				httpPostRequest.setEntity(urlEncodedFormEntity);
		    
			} catch (UnsupportedEncodingException e) {
				System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
				return "Erro a inserir parametros no POST";
			}
			
			
			HttpResponse response=null;
			try {
				response = httpclient.execute(httpPostRequest);
				HttpCode = response.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return "Erro de ClientProtocol";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "(Erro de IOException)";
			}catch (Exception e){
				return "Erro (EXCEPTION) na resposta do POST";
			}
			
				
		
			HttpEntity entity = response.getEntity();
			
			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : "+e.toString());
				return "Erro de IllegalStateException do entity.getContent()";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException do entity.getContent()"; 
			}catch (Exception e){
				return "Erro (EXCEPTION) na conteudo do Entity do POST";
			}								
			
			return convertStreamToString(instream);
						
		}
		
		public Object getUserDetailsJson(Object obj,Header h){

			if(URL==null) return "URL invalido";

			DefaultHttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpgetRequest = new HttpGet(URL);

		    if(h==null)return null;
		    httpgetRequest.addHeader("cookie",h.getValue());

		    HttpResponse response=null;
		    try {
				response = httpclient.execute(httpgetRequest);
				HttpCode = response.getStatusLine().getStatusCode();

			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return null;
			} catch (IOException e) {
				System.out.println("ERRO : "+e.toString());

				return null;
			}catch (Exception e){
				return null;
			}


		    HttpEntity entity = response.getEntity();

			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : " + e.toString());
				return null;//"Erro de IllegalStateException do entity.getContent()";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return null;//"Erro de IOException do entity.getContent()";
			}catch (Exception e){
				return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
			}

			String reader = convertStreamToString(instream);
			System.out.println("GSON STRING: "+ reader);
			Gson gson = new Gson();

			if(obj.getClass().getSimpleName().contains("User")){
				obj = gson.fromJson(reader, User.class);
				return obj;
			}

		    return null;
		}

    public Object sendUpdateGuidelines(Header h){

        if(URL==null) return "URL invalido";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if(h==null)return null;
        httpgetRequest.addHeader("cookie",h.getValue());

        HttpResponse response=null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return null;
        } catch (IOException e) {
            System.out.println("ERRO : "+e.toString());

            return null;
        }catch (Exception e){
            return null;
        }


        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : " + e.toString());
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return null;//"Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);

        return reader;
    }

    public Object sendGetGuidelines(Object obj,Header h){

        if(URL==null) return "URL invalido";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if(h==null)return null;
        httpgetRequest.addHeader("cookie",h.getValue());

        HttpResponse response=null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return null;
        } catch (IOException e) {
            System.out.println("ERRO : "+e.toString());

            return null;
        }catch (Exception e){
            return null;
        }


        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : " + e.toString());
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return null;//"Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }
        String reader = convertStreamToString(instream);
        System.out.println("GSON STRING: "+ reader);
        Gson gson = new Gson();

        if(obj.getClass().getSimpleName().contains("GuidelineList")){
            obj = gson.fromJson(reader, GuidelineList.class);
            return obj;
        }

        return null;
    }

    public Object sendPostNext(Object obj, Header h){

        if(URL==null) return "URL invalido";


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);

        if(h==null) return "Token: Sem permissao para efectuar o pedido";
        httpPostRequest.addHeader("cookie", h.getValue());

        try{
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
            return "Erro a inserir parametros no POST";
        }


        HttpResponse response=null;
        try {
            response = httpclient.execute(httpPostRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "(Erro de IOException)";
        }catch (Exception e){
            return "Erro (EXCEPTION) na resposta do POST";
        }



        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : "+e.toString());
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null;
        }
        String reader = convertStreamToString(instream);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        if(obj.getClass().getSimpleName().contains("ProcessedTask")&&HttpCode ==200){
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }
        return null;
    }

    public Object sendPostNext2(Object obj, Header h){

        if(URL==null) return "URL invalido";


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);

        if(h==null) return "Token: Sem permissao para efectuar o pedido";
        httpPostRequest.addHeader("cookie", h.getValue());

        try{
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
            return "Erro a inserir parametros no POST";
        }


        HttpResponse response=null;
        try {
            response = httpclient.execute(httpPostRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "(Erro de IOException)";
        }catch (Exception e){
            return "Erro (EXCEPTION) na resposta do POST";
        }



        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : "+e.toString());
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null;
        }
        String reader = convertStreamToString(instream);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        if(obj.getClass().getSimpleName().contains("ProcessedTask")&&HttpCode ==200){
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }
        return null;
    }


    public Object sendGetLastTask(Object obj,Header h){

        if(URL==null) return "URL invalido";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if(h==null)return null;
        httpgetRequest.addHeader("cookie",h.getValue());

        HttpResponse response=null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        }catch (Exception e){
            return null;
        }


        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .setPrettyPrinting()
                .create();

        if(obj.getClass().getSimpleName().contains("ProcessedTask")){
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }

        return null;
    }


    public Object sendGetFormula(Object obj,Header h){

        if(URL==null) return "URL invalido";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if(h==null)return null;
        httpgetRequest.addHeader("cookie",h.getValue());

        HttpResponse response=null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return null;
        } catch (IOException e) {
            System.out.println("ERRO : "+e.toString());

            return null;
        }catch (Exception e){
            return null;
        }


        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : " + e.toString());
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return null;//"Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        System.out.println("GSON STRING: "+ reader);
        Gson gson = new Gson();

        if(obj.getClass().getSimpleName().contains("Formula")){
            obj = gson.fromJson(reader, Formula.class);
            return obj;
        }

        return null;
    }


    public Object sendGetClinicalTask(Object obj,Header h){

        if(URL==null) return "URL invalido";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if(h==null)return null;
        httpgetRequest.addHeader("cookie",h.getValue());

        HttpResponse response=null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return null;
        } catch (IOException e) {
            System.out.println("ERRO : "+e.toString());

            return null;
        }catch (Exception e){
            return null;
        }


        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : " + e.toString());
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return null;//"Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        System.out.println("GSON STRING: "+ reader);
        Gson gson = new Gson();

        if(obj.getClass().getSimpleName().contains("Question")){
            obj = gson.fromJson(reader, Question.class);
            return obj;
        }
        if(obj.getClass().getSimpleName().contains("Plan")){
            obj = gson.fromJson(reader, Plan.class);
            return obj;
        }
        if(obj.getClass().getSimpleName().contains("Decision")){
            obj = gson.fromJson(reader, Decision.class);
            return obj;
        }
        if(obj.getClass().getSimpleName().contains("End")){
            obj = gson.fromJson(reader, End.class);
            return obj;
        }
        if(obj.getClass().getSimpleName().contains("Action")){
            obj = gson.fromJson(reader, Action.class);
            return obj;
        }

        return null;
    }


    public String sendForgotGET(){
			
			if(URL==null) return "URL invalido";
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpgetRequest = new HttpGet(URL);
		        
		    HttpResponse response=null;
		    
		    try {
				response = httpclient.execute(httpgetRequest);
				HttpCode = response.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return "Erro de ClientProtocol";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException";
			}catch (Exception e){
				return "Erro (EXCEPTION) na resposta do POST";
			}
		    
		    
		    HttpEntity entity = response.getEntity();
			
			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : "+e.toString());
				return "Erro de IllegalStateException do entity.getContent()";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException do entity.getContent()"; 
			}catch (Exception e){
				return "Erro (EXCEPTION) na conteudo do Entity do POST";
			}								
			
			return convertStreamToString(instream);
		    
		    
		}

		public String sendSearchGET(){

			if(URL==null) return INVALID_URL;

			DefaultHttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpgetRequest = new HttpGet(URL);

		    HttpResponse response=null;

		    try {
				response = httpclient.execute(httpgetRequest);
				HttpCode = response.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return CLI_EXCEPTION;
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return IO_EXCEPTION;
			}catch (Exception e){
				return CLI_EXCEPTION;
			}


		    HttpEntity entity = response.getEntity();

			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : "+e.toString());
				return CLI_EXCEPTION;
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return IO_EXCEPTION;
			}catch (Exception e){
				return CLI_EXCEPTION;
			}

			return convertStreamToString(instream);
		}
		
		public String sendRegistoPut(){
			
			if(URL==null) return "URL invalido";
			
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPut httpPutRequest = new HttpPut(URL);
			
			try{			
			    UrlEncodedFormEntity urlEncodedFormEntity;
				urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
				httpPutRequest.setEntity(urlEncodedFormEntity);
		    
			} catch (UnsupportedEncodingException e) {
				System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
				return "Erro a inserir parametros no POST";
			}
			
			
			HttpResponse response=null;
			try {
				response = httpclient.execute(httpPutRequest);
				HttpCode = response.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return "Erro de ClientProtocol";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException";
			}catch (Exception e){
				return "Erro (EXCEPTION) na resposta do POST";
			}
			
				
		
			HttpEntity entity = response.getEntity();
			
			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : "+e.toString());
				return "Erro de IllegalStateException do entity.getContent()";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException do entity.getContent()"; 
			}catch (Exception e){
				return "Erro (EXCEPTION) na conteudo do Entity do POST";
			}								
			
			return convertStreamToString(instream);
			
			
		}

    public String sendActionPut(Header h){

        if(URL==null) return "invalid url";


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);
        httpPutRequest.addHeader("cookie",h.getValue());

        try{
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
            return "Error inserting POST";
        }


        HttpResponse response=null;
        try {
            response = httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "Erro de IOException";
        }catch (Exception e){
            return "Erro (EXCEPTION) na resposta do POST";
        }



        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : "+e.toString());
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);


    }
    public String sendPut(Header h){

        if(URL==null) return "invalid url";


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);
        httpPutRequest.addHeader("cookie",h.getValue());

        try{
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
            return "Error inserting POST";
        }


        HttpResponse response=null;
        try {
            response = httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : "+e.toString());
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "Erro de IOException";
        }catch (Exception e){
            return "Erro (EXCEPTION) na resposta do POST";
        }



        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : "+e.toString());
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : "+e.toString());
            return "Erro de IOException do entity.getContent()";
        }catch (Exception e){
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);


    }
		public String sendLoginPost(){
				
			if(URL==null) return "URL invalido";

			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpPost httpPostRequest = new HttpPost(URL);
			System.out.println(URL+valuePairs.toString());
			try{			
			    UrlEncodedFormEntity urlEncodedFormEntity;
				urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
				httpPostRequest.setEntity(urlEncodedFormEntity);
		    
			} catch (UnsupportedEncodingException e) {
				System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
				return "Erro a inserir parametros no POST";
			}
			
			
			
			HttpResponse response=null;
			try {
				response = httpclient.execute(httpPostRequest);
				
				HttpCode = response.getStatusLine().getStatusCode();
				System.out.println(response.toString());
				cookieLogin = response.getFirstHeader("Set-Cookie");
                System.out.println(cookieLogin.toString());
			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return "Erro de ClientProtocol\n\n"+e.toString();
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException"+e.toString();
			}catch (Exception e){
				return "Erro (EXCEPTION) na resposta do POST"+e.toString();
			}
			
				
		
			HttpEntity entity = response.getEntity();
			
			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : "+e.toString());
				return "Erro de IllegalStateException do entity.getContent()";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException do entity.getContent()"; 
			}catch (Exception e){
				return "Erro (EXCEPTION) na conteudo do Entity do POST";
			}								
			
			return convertStreamToString(instream);
			
			
		}

		public String sendSupportPut(){
			
			if(URL==null) return "URL invalido";
			
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPut httpPutRequest = new HttpPut(URL);
			
			try{			
			    UrlEncodedFormEntity urlEncodedFormEntity;
				urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
				httpPutRequest.setEntity(urlEncodedFormEntity);
		    
			} catch (UnsupportedEncodingException e) {
				System.out.println("ERRO: UnUnsupportedEncodingException! : "+e.toString());
				return "Erro a inserir parametros do Support";
			}
			
			
			HttpResponse response=null;
			try {
				response = httpclient.execute(httpPutRequest);
				HttpCode = response.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				System.out.println("ERRO: ClientProtocolException : "+e.toString());
				return "Erro de ClientProtocol";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException";
			}catch (Exception e){
				return "Erro (EXCEPTION) Sem resposta do PUT";
			}
			
				
		
			HttpEntity entity = response.getEntity();
			
			InputStream instream;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				System.out.println("ERRO: IllegalStateException : "+e.toString());
				return "Erro de IllegalStateException do entity.getContent()";
			} catch (IOException e) {
				System.out.println("ERRO: IOException : "+e.toString());
				return "Erro de IOException do entity.getContent()"; 
			}catch (Exception e){
				return "Erro (EXCEPTION) na conteudo do Entity do PUT";
			}								
			
			return convertStreamToString(instream);
			
		}
		
		
		public Integer getHttpCode() {
			return HttpCode;
		}

		public ArrayList<BasicNameValuePair> getValuePairs() {
			return new ArrayList<BasicNameValuePair>(valuePairs);
		}

		public void setValuePairs(ArrayList<BasicNameValuePair> valuePairs) {
			this.valuePairs = valuePairs;
		}

		public void setHttpCode(Integer httpCode) {
			HttpCode = httpCode;
		}

		public Header getCookieValidade() {
			return cookieValidade;
		}
		
		public String getCookieValidadeToString(){
			return cookieValidade.getValue();
		}

		public void setCookieValidade(Header cookieValidade) {
			this.cookieValidade = cookieValidade;
		}

		public Header[] getHeaders() {
			return headers;
		}

		public void setHeaders(Header[] headers) {
			this.headers = headers;
		}

		
		public String getURL() {
			return URL;
		}

		public void setURL(String uRL) {
			URL = uRL;
		}

		public Header getCookieLogin() {
			return cookieLogin;
		}
		

		public void setCookieLogin(Header cookieLog) {
			cookieLogin = cookieLog;
		}
}

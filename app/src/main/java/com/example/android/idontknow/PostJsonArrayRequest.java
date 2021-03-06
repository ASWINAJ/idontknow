package com.example.android.idontknow;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aswin on 5/4/16.
 */

    public  class PostJsonArrayRequest extends Request<JSONArray> {
        private Map<String,String> mParam;
        private Response.Listener<JSONArray> mListener;


        public PostJsonArrayRequest(String url,Response.Listener<JSONArray> listener, Response.ErrorListener errorListener,Map param) {
            super(Request.Method.POST, url, errorListener);
            mListener=listener;
            mParam=param;
        }
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return mParam;
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString =
                        new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONArray(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected void deliverResponse(JSONArray response) {
            mListener.onResponse(response);

        }

    }
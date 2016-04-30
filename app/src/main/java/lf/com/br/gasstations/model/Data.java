package lf.com.br.gasstations.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fernando on 21/04/2016.
 */
public class Data {

    @SerializedName("busca")
    public List<Posto> postos;
    public String status;
    public String detail;
    public String resquest_uri;
    public String created_at;
    public String elapsed_time;
}

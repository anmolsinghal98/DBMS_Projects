package Assignment1;

import java.io.*;
import java.util.ArrayList;

class Field{
    private String name;
    private String type;
    private String size;
    Field(String name,String type,String size){
        this.name=name;
        this.type=type;
        this.size=size;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {

        File data_file=new File("db.data");
        File meta_file=new File("metadata.txt");

        BufferedReader br1=new BufferedReader(new FileReader(data_file));
        BufferedReader br2=new BufferedReader(new FileReader(meta_file));

        ArrayList<Field> structure=new ArrayList<Field>();

        String r=br2.readLine();
        while(r!=null){
            String[] a=r.split(",");
            Field f=new Field(a[0],a[1],a[2]);
            structure.add(f);
            r=br2.readLine();
        }

        for(int i=0;i<structure.size();i++){
            System.out.println("Field Name: "+structure.get(i).getName()+", Field Type: "+structure.get(i).getType()+", Field Size: "+structure.get(i).getSize());
        }

        System.out.println();
        System.out.println("Finish reading data description file...");
        System.out.println();

        String[] records=new String[100];
        int index=0;

        r=br1.readLine();
        while(r!=null){
            records[index]=r;
            r=br1.readLine();
            index++;
        }
        br1.close();
        for(int i=0;i<3;i++){
            System.out.println(records[i]);
        }

    }
}


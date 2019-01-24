package Assignment1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Field{
    private String name;
    private String type;
    private String size;
    private int id;
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

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


}



public class Main {

    public static void main(String[] args) throws IOException {

        File data_file=new File("db.data");
        File meta_file=new File("metadata.txt");

        BufferedReader br1=new BufferedReader(new FileReader(data_file));
        BufferedReader br2=new BufferedReader(new FileReader(meta_file));

        ArrayList<Field> structure=new ArrayList<>();

        String r=br2.readLine();
        int inticount = -1;
        int fcount = -1;
        int charcount = -1;
        int doublecount=-1;

        while(r!=null){
            String[] a=r.split(",");
            Field f=new Field(a[0],a[1],a[2]);
            if(a[1].equals("I")){
                inticount++;
                f.setId(inticount);
            }
            else if(a[1].equals("F")){
                fcount++;
                f.setId(fcount);
            }
            else if(a[1].equals("C")){
                charcount++;
                f.setId(charcount);
            }
            else if(a[1].equals("D")){
                doublecount++;
                f.setId(doublecount);
            }
            structure.add(f);
            r=br2.readLine();
        }
        ArrayList intis[] = new ArrayList[inticount+1];
        ArrayList floats[] = new ArrayList[fcount+1];
        ArrayList chars[] = new ArrayList[charcount+1];
        ArrayList doubles[] = new ArrayList[doublecount+1];

        for(int i = 0;i<inticount+1;i++){
            intis[i] = new ArrayList<Integer>();
        }

        for(int i = 0;i<fcount+1;i++){
            floats[i] = new ArrayList<Float>();
        }

        for(int i = 0;i<charcount+1;i++){
            chars[i] = new ArrayList<String>();
        }

        for(int i=0;i<doublecount+1;i++){
            doubles[i]=new ArrayList<Double>();
        }

        for(int i=0;i<structure.size();i++){
            System.out.println("Field Name: "+structure.get(i).getName()+", Field Type: "+structure.get(i).getType()+", Field Size: "+structure.get(i).getSize());
        }

        System.out.println();
        System.out.println("Finish reading data description file...");
        System.out.println();

        Scanner sc = new Scanner(data_file);

        while(sc.hasNext()){
            String str = sc.nextLine();
            System.out.println(str);
            int start = 0;
            for(int j = 0;j<structure.size();j++){
                Field field = structure.get(j);
                String type = field.getType();

                int bound = Integer.parseInt(field.getSize());
                int id = field.getId();

                String req = "";

                if(type.equals("I")){
                    for(int f = start;f < start+bound;f++){
                        if(f >= str.length()){
                            try{
                                int parsed = Integer.parseInt(req);
                                intis[id].add(parsed);
                            }
                            catch (Exception e){
                                System.out.println(e);
                            }
                            break;
                        }
                        if(str.substring(f,f+1).equals(" ")){
                            try{
                                int parsed = Integer.parseInt(req);
                                intis[id].add(parsed);
                            }
                            catch (Exception e){
                                System.out.println(e);
                            }
                            break;
                        }
                        else{
                            req += str.substring(f,f+1);
                        }
                    }
                }
                else if(type.equals("F")){
                    for(int f = start;f < start+bound;f++){
                        if(f >= str.length()) {
                            try{
                                float parsed = Float.parseFloat(req);
                                floats[id].add(parsed);
                            }
                            catch (Exception e){
                                System.out.println(e);
                            }
                            break;
                        }
                        if(str.substring(f,f+1).equals(" ")){
                            try{
                                float parsed = Float.parseFloat(req);
                                floats[id].add(parsed);
                            }
                            catch (Exception e){
                                System.out.println(e);
                            }
                            break;
                        }
                        else{
                            req += str.substring(f,f+1);
                        }
                    }
                }
                else if(type.equals("C")){
                    for(int f = start;f < start+bound;f++){
                        if(f >= str.length()){
                            chars[id].add(req);
                            break;
                        }
                        if(str.substring(f,f+1).equals(" ")){
                            chars[id].add(req);
                            break;
                        }
                        else{
                            req += str.substring(f,f+1);
                        }
                    }
                }
                else if(type.equals("D")){
                    for(int f=start;f<start+bound;f++){
                        if(f>=str.length()){
                            Double d=Double.parseDouble(req);
                            doubles[id].add(d);
                            break;
                        }
                        if(str.substring(f,f+1).equals(" ")){
                            try{
                                Double d=Double.parseDouble(req);
                                doubles[id].add(d);
                            }
                            catch(Exception e){
                                System.out.println(e);
                            }
                            break;
                        }
                        else{
                            req+=str.substring(f,f+1);
                        }
                    }
                }
                start += bound;
                //System.out.println(req);
            }

        }

        for(int i = 0;i<inticount+1;i++){
            Collections.sort(intis[i]);
        }

        for(int i = 0;i<fcount+1;i++){
            Collections.sort(floats[i]);
        }

        for(int i = 0;i<charcount+1;i++){
            Collections.sort(chars[i]);
        }

        for(int i=0;i<doublecount+1;i++){
            System.out.println(doubles[i]);
        }

        int flag=0;
        String max_field=args[0];
        for(int i=0;i<structure.size();i++){
            if(max_field.equals(structure.get(i).getName())){
                flag++;
                if(structure.get(i).getType().equals("I")){
                    System.out.println("Maximum value in field" + structure.get(i).getName() + ": " + intis[structure.get(i).getId()].get(intis[structure.get(i).getId()].size()-1));
                }
                else if(structure.get(i).getType().equals("C")){
                    System.out.println("Maximum value in field" + structure.get(i).getName() + ": " + chars[structure.get(i).getId()].get(chars[structure.get(i).getId()].size()-1));
                }
                else if(structure.get(i).getType().equals("F")){
                    System.out.println("Maximum value in field" + structure.get(i).getName() + ": " + floats[structure.get(i).getId()].get(floats[structure.get(i).getId()].size()-1));
                }
                else{
                    if(structure.get(i).getType().equals("D")){
                        System.out.println("Maximum value in field" + structure.get(i).getName() + ": " + doubles[structure.get(i).getId()].get(doubles[structure.get(i).getId()].size()-1));
                    }
                }
            }
        }
        if(flag == 0) System.out.println("Error: Field name not found.");

    }
}


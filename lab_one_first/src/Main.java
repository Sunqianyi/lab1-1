import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    private final  int maxnum=1000;
    private final  int maxint = 999999;
    private int vexs_number;
    private String[] vexs;
    private String[][] edges;
    private int[][] edgeWeight;
    private int[] dist;     // 表示当前点到源点的最短路径长度
    private int[] prev;     // 记录当前点的前一个结点
    private int[][] c;   // 记录图的两点间路径长度
    private int line;             // 图的结点数和路径数
    public  int flag=0;
    public  Main()throws Exception{
        JFrame frame = new JFrame("软件工程实验一");
        frame.setBounds(500, 200, 330, 180);
        frame.setLayout(null);
        JFileChooser chooser = new JFileChooser();
        JButton button0 = new JButton("确定");
        JButton button = new JButton("Find");
        JLabel label1 = new JLabel("输入路径：");
        final JTextField[] text = {new JTextField()};
        label1.setFont(new Font("Serif",Font.PLAIN,16));
        label1.setBounds(2, 25, 100, 60);
        text[0].setBounds(80, 40, 165, 30);
        button.setBounds(250,40,60,30);
        button0.setBounds(230, 90, 80, 40);
        frame.add(label1);
        frame.add(text[0]);
        frame.add(button0);
        frame.add(button);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final String[] input = {text[0].getText()};
        button0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                    try {
                        Graph(input[0]);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                if (flag == 1) {
                    JFrame frame1 = new JFrame();
                    //JButton button1 = new JButton("生成有向图");
                    JButton button2 = new JButton("展示有向图");
                    JButton button3 = new JButton("查询桥接词");
                    JButton button4 = new JButton("生成新文本");
                    JButton button5 = new JButton("最短路径");
                    JButton button6 = new JButton("随机游走");

                    frame1.setBounds(500, 200, 300, 500);
                    frame1.setLayout(null);
                    //button1.setBounds(100, 10, 100, 50);
                    button2.setBounds(100, 80, 100, 50);
                    button3.setBounds(100, 150, 100, 50);
                    button4.setBounds(100, 220, 100, 50);
                    button5.setBounds(100, 290, 100, 50);
                    button6.setBounds(100, 360, 100, 50);
                    //frame1.add(button1);
                    frame1.add(button2);
                    frame1.add(button3);
                    frame1.add(button4);
                    frame1.add(button5);
                    frame1.add(button6);
                    frame1.setVisible(true);

                    button2.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showDirectedGraph();
                            String[] cmd = new String[5];
                            String url = "f:\\Graph.jpg";
                            cmd[0] = "cmd";
                            cmd[1] = "/c";
                            cmd[2] = "start";
                            cmd[3] = " ";
                            cmd[4] = url;
                            try {
                                Runtime.getRuntime().exec(cmd);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });


                    button3.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String word1 = JOptionPane.showInputDialog(null, "word1");
                            String word2 = JOptionPane.showInputDialog(null, "word2");
                            word1 = word1.toLowerCase();
                            word2 = word2.toLowerCase();
                            JOptionPane.showMessageDialog(null, queryBridgeWords(word1, word2),
                                    "BridgeWords", JOptionPane.INFORMATION_MESSAGE);


                        }
                    });


                    button4.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String inputtext = JOptionPane.showInputDialog(null, "请输入文本");
                            inputtext = inputtext.toLowerCase();
                            JOptionPane.showMessageDialog(null, generateNewText(inputtext),
                                    "NewText", JOptionPane.INFORMATION_MESSAGE);

                        }
                    });

                    button5.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String word1 = JOptionPane.showInputDialog(null, "请输入路径起点");
                            String word2 = JOptionPane.showInputDialog(null, "请输入路径终点");
                            word1 = word1.toLowerCase();
                            word2 = word2.toLowerCase();
                            JOptionPane.showMessageDialog(null, calcShortestPath(word1, word2),
                                    "ShortPath", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                    button6.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String[] arr = randomWalk().split("\\s+");
                            List<String> list = new ArrayList(Arrays.asList(arr));

                            JFrame jFrame = new JFrame();//创建一个文本框
                            JTextPane messagePane = new JTextPane();
                            JButton insertBtn = new JButton("继续");

                            jFrame.getContentPane().add(messagePane, BorderLayout.CENTER);//进行布局
                            jFrame.getContentPane().add(insertBtn, BorderLayout.SOUTH);

                            SimpleAttributeSet attrset = new SimpleAttributeSet();//设置字体大小
                            StyleConstants.setFontSize(attrset, 16);

                            JScrollPane jsp = new JScrollPane(messagePane);//滚动条
                            jFrame.add(jsp);

                            insertBtn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String str;
                                    if (list.size() != 0) str = list.get(0);
                                    else str = new String("遍历已经结束");

                                    Document docs = messagePane.getDocument();//获得文本对象
                                    try {
                                        docs.insertString(docs.getLength(), str + "\n", attrset);//对文本进行追加
                                    } catch (BadLocationException e1) {
                                        e1.printStackTrace();
                                    }

                                    list.remove(0);
                                }
                            });

                            jFrame.setSize(200, 300);
                            jFrame.setVisible(true);


                        }
                    });

                }
                else
                {
                    JOptionPane.showMessageDialog(null,"有向图尚未生成");
                }
            }



            });
        button.addActionListener(new ActionListener(){


            public String txt;

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = chooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    this.txt  = chooser.getSelectedFile().getPath();
                    input[0] = txt;
                }
            }

        });
        }
    public   static  void main (String [] args)throws Exception{
        Main m=new Main();

    }
     private  void showDirectedGraph() {

        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        for(int i=0;i<this.vexs.length;i++){
             for(int j=0;j<this.vexs.length;j++){
                 if(this.edgeWeight[i][j] !=0 ){
                     String s= this.vexs[i]+"->"+this.vexs[j]+"[ label = "+this.edgeWeight[i][j]+"]";
                     gv.addln(s);
                 }
             }
        }
        gv.addln(gv.end_graph());
        String type = "jpg";
        File f1 = new File("f:\\Graph." + type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), f1);
    }
     String[] getWord(File file) throws Exception//从文件中读取
    {
        int i = 0;
        String temp[] = new String[1000];
        if(file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            while (true) {
                String str = in.readLine();

                if (str == null) {
                    break;
                }
                temp[i] = str;
                i++;
            }

            String ip[] = new String[i];

            for (int j = 0; j < i; j++) {
                ip[j] = temp[j];
            }
            this.flag = 1;
            System.out.println(flag);
            return ip;
        }
        else{
            flag=0;
            return null;
        }
    }
    //字符串的处理
    static String[] String_handle(String s1){
        //s1=s1.replaceAll("[^a-zA-Z]","");
        String[] s2 = s1.split("[^a-zA-Z]");
        int j =0;
        for(int i=0;i<s2.length;i++){
            if(s2[i].equals("")){}
            else {
               j++;
            }
        }
        String[] s3 = new String[j];
        int x=0;
        for(int i=0;i<s2.length;i++){
            if(s2[i].equals("")){}
            else {
                s3[x] = s2[i];
                x++;
            }
        }
        return s3;
    }
    //字符串的拼接
    static String[] String_concat(String[] s1, String[] s2){
        String[] s3= null ;
        if(s1== null){
            s3=s2;
        }
        else {
            s3 = new String[s1.length + s2.length];
            System.arraycopy(s1, 0, s3, 0, s1.length);
            System.arraycopy(s2, 0, s3, s1.length, s2.length);
        }
        return s3;
    }
    static int String_compare(String[] str,String s,int j){
        for(int i = 0; i<j; i++){
            if(str[i].equals(s)) {
                return -1;

            }
        }
        return 1;
    }
    void Graph(String f)throws Exception{
        File file = new File(f);
        String ip[] = getWord(file);
        this.line = 0;
        this.vexs_number=0;
        String[] list_1=null;
        this.vexs = new String[1000];
        this.edges = new String[1000][1000];
        for(int i = 0; i < ip.length; i ++) {
            //System.out.println(ip[i]);
            list_1 = String_concat(list_1, (String_handle(ip[i])));
        }
        for(int i=0;i<list_1.length;i++){
            list_1[i]=list_1[i].toLowerCase();
        }
        for(int i=0;i<list_1.length;i++){

            if(this.vexs_number == 0) {
                this.vexs[vexs_number]=list_1[i];
                this.vexs_number++;
            }

            else {
                if( String_compare(this.vexs,list_1[i],this.vexs_number) == 1 ) {
                    this.vexs[vexs_number] = list_1[i];
                    this.vexs_number++;
                }
            }

        }
        for (int i=0 ;i<list_1.length-1;i++){
            this.edges[i][0]=list_1[i];
            this.edges[i][1]=list_1[i+1];
        }


         this.edgeWeight = new int[1000][1000];

        for(int i=0;i<this.vexs_number;i++ ){
            //System.out.print(vexs[i]+" ");
            for(int j=0;j<this.vexs_number;j++){
                int weight = 0;
                String[] edge = {this.vexs[i],this.vexs[j]};
                for(int m = 0;m<list_1.length-1;m++){
                    //System.out.println(edge[0]+"->"+edge[1]+"&&"+edges[m][0]+"->"+edges[m][1]);
                    if(edge[0].equals(this.edges[m][0] )&& edge[1].equals(this.edges[m][1])){
                        weight++;
                    }
                    this.edgeWeight[i][j] = weight;
                    if(weight!=0) this.line+=1;
                }
            }

        }

    }
    //查询桥接词
    String queryBridgeWords(String word1 ,String word2  ){
        StringBuilder sb = new StringBuilder();
        int bridgeNum = 0;
        if(getIndex(word1)==-1){
            System.out.println("No "+word1);
            return "######"+"No "+word1;
        }
        else if(getIndex(word2)==-1){
            System.out.println("No "+word2);
            return "######"+"No "+word2;
        }
        else{
            for(int x=0;x<this.vexs_number;x++){
                if( this.edgeWeight[getIndex(word1)][x]!=0&&this.edgeWeight[x][getIndex(word2)]!=0){
                    sb.append(this.vexs[x]);
                    sb.append(" ");
                    bridgeNum++;
                }
            }
            if(bridgeNum==0) {
                System.out.println("不存在的");
                return "###不存在的";
            }
            else{
                String sub = sb.toString();
                System.out.println(sub);
                return sub;
            }
        }
    }
    //获得索引值
    int getIndex(String word){
        int index=-1;
        for(int i =0;i<this.vexs_number;i++){
            if(word.equals(this.vexs[i])) {
                index=i;
            }
        }
        return index;
    }
    //补全文本
    String generateNewText(String inputText){
        StringBuilder sb = new StringBuilder();
        String[] str = String_handle(inputText);
        for(int i = 0;i<str.length-1;i++){

            if((queryBridgeWords(str[i],str[i+1]).substring(0,1)).equals("#")) {
                System.out.println(queryBridgeWords(str[i],str[i+1]).substring(0,1));
                sb.append(str[i]);
                sb.append(" ");
            }
            else{
                System.out.println(queryBridgeWords(str[i],str[i+1]).substring(0,1));
                String[] bridgewords=String_handle(queryBridgeWords(str[i],str[i+1]));
                int index = (int) (Math.random() * bridgewords.length);
                sb.append(str[i]);
                sb.append(" ");
                sb.append(bridgewords[index]);
                sb.append(" ");
            }
        }
        sb.append(str[str.length-1]);
        String sub = sb.toString();
        System.out.println(sub);
        return sub;
    }
    //随机游走
    String randomWalk(){
        String ran = "";
        int[][] visited = new int[50][2];
        int visited_num =0;
        int src1 = (int) (Math.random() * vexs_number);
        int temp,src2;
        do{
            temp=src1;
            src2=_randomWalk(src1);
            visited[visited_num][0]=src1;
            visited[visited_num][1]=src2;
            visited_num++;
            src1=src2;
        }while (numInArray(visited,temp,src2,visited_num-1)==false&&src2!=-1);
        for(int i=0;i<=visited_num-1;i++){
            ran = ran + this.vexs[visited[i][0]] +" ";
            System.out.println(this.vexs[visited[i][0]]);
        }
        return ran;
    }
    int _randomWalk(int str_index){
        List Str  = new ArrayList();
        for(int i=0;i<vexs_number;i++){
            if(this.edgeWeight[str_index][i]!=0){
                Str.add(i);
            }
        }
        Object[] array = Str.toArray();
        int[] Array= new int[array.length];
        for(int j=0;j<array.length;j++){
            Array[j] = (Integer)array[j];
        }
        if (array.length==0) return -1;
        else{
            int src = (int) (Math.random() * array.length);
            return Array[src];
        }
    }
    boolean numInArray(int[][] array,int num1,int num2,int len){
        int temp=0;
        for(int i = 0;i< len;i++){
            if(num1==array[i][0]&&num2==array[i][1]) temp++;
        }
        if(temp==0) return false;
        else return true;
    }
    void Dijkstra(int n, int v) {
        this.dist=new int[maxnum];     // 表示当前点到源点的最短路径长度
        this.prev=new int[maxnum];     // 记录当前点的前一个结点
        this.c=new int[maxnum][maxnum];   // 记录图的两点间路径长度
        int[] s=new int[maxnum];    // 判断是否已存入该点到S集合中
        for(int i=0; i<this.vexs_number;i++)//将邻接矩阵传入c中当做最原始的两点之间路径的长度
            for(int j=0;j<this.vexs_number;j++){
                if(this.edgeWeight[i][j]==0) c[i][j]=maxint;
                else   c[i][j]=edgeWeight[i][j];
            }
        for(int i=0; i<n; ++i)
        {
            dist[i] = c[v][i];
            s[i] = 0;     // 初始都未用过该点
            if(dist[i] == maxint)
                prev[i] = 0;
            else
                prev[i] = v;
        }
        dist[v] = 0;
        s[v] = 1;

        // 依次将未放入S集合的结点中，取dist[]最小值的结点，放入结合S中
        // 一旦S包含了所有V中顶点，dist就记录了从源点到所有其他顶点之间的最短路径长度
        // 注意是从第二个节点开始，第一个为源点
        for(int i=1; i<n; ++i)
        {
            int tmp = maxint;
            int u = v;
            // 找出当前未使用的点j的dist[j]最小值
            for(int j=0; j<n; ++j)
                if((s[j]==0) && dist[j]<tmp)
                {
                    u = j;              // u保存当前邻接点中距离最小的点的号码
                    tmp = dist[j];
                }
            s[u] = 1;    // 表示u点已存入S集合中

            // 更新dist
            for(int j=0; j<n; ++j)
                if((s[j]==0) && c[u][j]<maxint)
                {
                    int newdist = dist[u] + c[u][j];
                    if(newdist < dist[j])
                    {
                        dist[j] = newdist;
                        prev[j] = u;
                    }
                }
        }
    }
    String searchPath(int v, int u) {
        int[] que = new int[maxnum];
        int tot = 0;
        String str="";
        que[tot] = u;
        tot++;
        int tmp = this.prev[u];
        while(tmp != v)
        {
            que[tot] = tmp;
            tot++;
            tmp = prev[tmp];
        }
        que[tot] = v;
        for(int i=tot; i>=0; --i)
            if(i != 0) {
                System.out.print(que[i] + "->");
                str = str + vexs[que[i]] + "->";
            }
            else {
                System.out.println(que[i]);
                str = str + vexs[que[i]] ;
            }
            return str;
    }
    String calcShortestPath(String world1,String world2){
          int v;
          int u;
          String str="";
          v=getIndex(world1);
          u=getIndex(world2);
          Dijkstra(vexs_number,v);
          str=searchPath(v,u);
          return  str;
    }
}
//加入git管理
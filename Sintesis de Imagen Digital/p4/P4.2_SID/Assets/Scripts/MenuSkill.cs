using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MenuSkill : MonoBehaviour
{

    public int numberSides;

    [System.Serializable]
    public struct skill { public string name; public float power; }

    public skill[] skills;
    

    public float centerX, centerY;

    public float scaleFactor;

    public Material mat;

    private float maxPower = 100;

    private Mesh mesh;

    // Start is called before the first frame update
    void Start()
    {

        mesh = new Mesh();
        GetComponent<MeshFilter>().mesh = mesh;
        mesh.name = "Procedural Mesh";

        calculatePositions();
        GetComponent<MeshRenderer>().material = mat;

    }

    void calculatePositions()
    {   
       
        //Calcular primer punto
        Vector3[] vertices_total = new Vector3[numberSides+1];

        //centro
        
        vertices_total[0] = new Vector3(0, 0, 0);
        print("centro = " + vertices_total[0]);

        //primer punto
        vertices_total[1] = new Vector3(3,0,0) ;

        //Calcular los siguientes puntos
        for (int i = 1; i < numberSides; i++)
        {
            float angle = 2 * Mathf.PI / numberSides * i;
            vertices_total[i+1] = new Vector3(Mathf.Cos(angle) * 3, Mathf.Sin(angle) * 3, 0) + vertices_total[0];
            print("i = "+ i + vertices_total[i+1]);


        }

        //Crear otro vector para añadir los puntos obtenidos con Lerp
        Vector3[] vertices = new Vector3[numberSides+1];
        vertices[0] = new Vector3(0,0,0) ;
        float aux_power;
        for (int i = 1; i < numberSides+1;i++)
        {
            aux_power = skills[i-1].power;
            aux_power = aux_power / maxPower;
            vertices[i].x = Mathf.Lerp(vertices_total[0].x, vertices_total[i].x, aux_power);
            vertices[i].y = Mathf.Lerp(vertices_total[0].y, vertices_total[i].y, aux_power);
        }
        mesh.vertices = vertices;


        int[] triangles = new int[(numberSides) * 3];
        triangles[0] = 0;
        triangles[1] = 1;
        triangles[2] = 2;
        for (int i = 3; i < (numberSides - 1) * 3; i++)
        {
            int aux = i % 3;
            if (aux == 0)
            {
                triangles[i] = triangles[0];
            }
            else
            {
                triangles[i] = triangles[i - 3] + 1;
            }
        }
        triangles[triangles.Length - 3] = triangles[0];
        triangles[triangles.Length - 2] = triangles[triangles.Length - 4];
        triangles[triangles.Length - 1] = triangles[1];
        mesh.triangles = triangles;

    }

        // Update is called once per frame
        void Update()
    {
       
    }


}

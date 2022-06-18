using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(MeshFilter)), RequireComponent(typeof(MeshRenderer))]
public class MeshGenerator : MonoBehaviour
{

    public int Number_Sides, Radio, Center_X, Center_Y;

    public Vector2[] center = new Vector2[1];

    public Material mat;

    private Mesh mesh;


    // Start is called before the first frame update
    void Start()
    {
        Generate();
    }

    private void Generate()
    {
        mesh = new Mesh();
        GetComponent<MeshFilter>().mesh = mesh;
        mesh.name = "Procedural Mesh";

        //Calcular vertices con código del 3.1




        calculatePositions();
        GetComponent<MeshRenderer>().material = mat;

    }

    void calculatePositions()
    {   //Calcular primer punto
        int n = 0;
        Vector3[] vertices = new Vector3[Number_Sides];
        vertices[0] = new Vector3(Radio + Center_X, 0 + Center_Y, 0);
        print("primero : " + vertices[0]);
        //Calcular los siguientes puntos
        for (int i = 1; i < Number_Sides; i++)
        {
            float angle = 2 * Mathf.PI / Number_Sides * i;
            vertices[i] = new Vector3(Mathf.Cos(angle) * Radio + Center_X, Mathf.Sin(angle) * Radio + Center_Y, 0);
            print("i = " + vertices[i]);


        }
        mesh.vertices = vertices;

        int[] triangles = new int[(Number_Sides - 2) * 3];
        triangles[0] = 0;
        triangles[1] = 1;
        triangles[2] = 2;
        for (int i = 3; i < (Number_Sides - 2) * 3; i++)
        {
            int aux = i % 3;
            if (aux == 0)
            {
                triangles[i] = triangles[i - 3];
            }
            else
            {
                triangles[i] = triangles[i - 3] + 1;
            }

        }
        mesh.triangles = triangles;
    }
    // Update is called once per frame
    void Update()
    {

    }
}

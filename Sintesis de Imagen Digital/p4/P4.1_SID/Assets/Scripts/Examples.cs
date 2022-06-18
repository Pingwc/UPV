using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Examples : MonoBehaviour
{
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

        Vector3[] vertices = { new Vector3 { x = 2, y = 0, z = 0 },
                               new Vector3 {x = 1, y = 1.7f, z = 0 },
                               new Vector3 {x = -1, y = 1.7f, z = 0 },
                               new Vector3 {x = -2, y = 0, z = 0 },
                               new Vector3 {x = -1, y = -1.7f, z = 0 },
                               new Vector3 {x = 1, y = -1.7f, z = 0 }, };
        mesh.vertices = vertices;

        int[] triangles = { 0, 1, 2, 0, 2, 3 ,0, 3, 4, 0, 4, 5};
        mesh.triangles = triangles;

        GetComponent<MeshRenderer>().material = mat;
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}

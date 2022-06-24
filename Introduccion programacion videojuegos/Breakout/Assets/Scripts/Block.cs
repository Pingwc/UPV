using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Block : MonoBehaviour
{
    public Material material;
    public Material material1;
    public Material material2;




    MeshRenderer mesh;
    // Start is called before the first frame update
    void Start()
    {
        mesh = GetComponent<MeshRenderer>();
        LevelManager.numInitialBlocks++; //Contamos los bloques que hay en el nivel

    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void OnCollisionEnter(Collision collision)
    {
        
        switch(mesh.material.name)
        {
            case "m_blueblock (Instance)":
                mesh.material = new Material(material1);
                break;
            case "m_yellowblock (Instance)":
                mesh.material = new Material(material2);
                break;
            case "m_redblock (Instance)":
                Destroy(gameObject);
                LevelManager.numInitialBlocks--;
                break;
            default:
                break;
        }
       
    }
}

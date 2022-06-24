using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class level2 : MonoBehaviour
{
    private  float leftLimit = -4.0f;
    private  float rightLimit = 4.0f;

    private float x = -4.0f;
    private float x2 = -3.4f;
    private float x1 = -4.0f;


    private float y = 0.25f;
    private float z = 4.25f;
    private bool rowComplete = false;

    private int n_blue;
    private int n_yellow;


    public GameObject yellowblock;
    public GameObject redblock;

    public GameObject blueblock;


    // Start is called before the first frame update
    void Start()
    {
        n_blue =Random.Range(0, 7);
        n_yellow= Random.Range(2, 10);
        create();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    void create ()
    {
        //number of rows
        for(int i = 0; i < 5; i++)
        {
            //number of blocks on Y axis(horizontal)
            for(int j = 0; j < 8; j++)
            {

                //Use RNG to make each block have 33% chance to be each color
                int random = Random.Range(0,100);
                if(random < 33 && n_yellow != 0)
                {
                    GameObject go = GameObject.Instantiate(yellowblock);
                    go.transform.position = new Vector3(x, y, z);
                    x = x + 1.0f;
                    n_yellow--;
                } else if(random > 32 && random <65 && n_blue !=0)
                {
                    GameObject go = GameObject.Instantiate(blueblock);
                    go.transform.position = new Vector3(x, y, z);
                    x = x + 1.0f;
                    n_blue--;
                } else
                {
                    GameObject go = GameObject.Instantiate(redblock);
                    go.transform.position = new Vector3(x, y, z);
                    x = x + 1.0f;
                }
               // GameObject go = GameObject.Instantiate(redblock);
                //go.transform.position = new Vector3(x, y, z);
               // x = x + 1.0f;
            }
            //Check if the row of blocks is complete??
            if(rowComplete == false)
            {
                x = x2;
                rowComplete = true;
            }else
            {
                x=x1;
                rowComplete = false;
            }
            z = z - 0.7f;
        }
        
    }
}

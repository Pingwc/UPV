using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Buttons_Management : MonoBehaviour
{

    public GameObject screen;
    //public GameObject screen2;

    public AudioSource musica;

    public Text blocks;

    private bool p = false;


    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

        if(Input.GetKeyDown(KeyCode.Escape))
        {
            if(p == false)
            {
                Time.timeScale = 0;
                //screen2.SetActive(false);
                screen.SetActive(true);
                musica.Pause();
                blocks.text = "blocks left: " + LevelManager.numInitialBlocks.ToString();
                p = true;
            }
            else
            {
                screen.SetActive(false);
                //screen2.SetActive(true);
                Time.timeScale = 1;
                musica.Play();
                p = false;
            }
        }
    }

    public void buttonPause()
    {
        Time.timeScale = 0;
       // screen2.SetActive(false);
        screen.SetActive(true);
        musica.Pause();

    }

    public void buttonResume()
    {
        screen.SetActive(false);
        //screen2.SetActive(true);
        Time.timeScale = 1;
        musica.Play();

    }

    public void buttonRestart()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        Time.timeScale = 1;
    }

    public void buttonPlay()
    {
        SceneManager.LoadScene("Level1");
    }
}

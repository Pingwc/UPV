using UnityEngine.SceneManagement;
using UnityEngine;
using System.Collections;
public class LevelObject : MonoBehaviour
{

    private AssetBundle myLoadedAssetBundle;
    private string[] scenePaths;

    // Use this for initialization
    void Start()
    {
        
    }
    // Update is called once per frame
    void Update()
    {
        if (LevelManager.numInitialBlocks == 0)
        {
            switch(SceneManager.GetActiveScene().name)
            {
                case "Level1":
                    SceneManager.LoadScene("Scenes/Level2");
                    break;
                case "Scene/Level2":
                    SceneManager.LoadScene("Scenes/Level2");
                    break;
                default:
                    break;
            }
           // SceneManager.LoadScene("Scenes/Level2");
        }
            
    }
}
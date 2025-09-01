package jhonn.deere.code.challenge;

import com.github.dockerjava.api.DockerClient;

public class DockerController {


    private DockerClient dockerClient;

    public DockerController() {
        // initialize DockerClient
    }

    public boolean startDatabase() {
        // Implement logic to start a Docker container with your database
        return true;
    }


}

package b_theory.question9;

public class Docker {

    //todo A
    // What is a server?
    // Answer: A server is a computer that provides data to other computer.
    // It does it by runnig some specific applications that decide what to do with incoming and outcoming data

    //todo B
    // What is the difference between build server and production server?
    // Answer: production server is the one that normal users see when they access the site
    // build server is a server that builds the pushed code so it would work on every other machine

    //todo C
    // What is docker?
    // Answer: service that allows software to be run in "containers" where outside variables will not interfere

    //todo D
    // Name and explain docker container benefits over virtual machine setup (java jar as system process and installed nginx)
    // 1 docker containers occupy less space, since they are optimised for running specific applications rather than being a full OS
    // 2 docker builds have stages so it is faster and easier to troubleshoot and design them to fulfill your needs

    //todo E
    // Name and explain docker container drawback over virtual machine setup (java jar as system process and installed nginx)
    // 1 all docker contrainers share 1 kernel which can be a security issue

    //todo F
    // Name and describe tools for docker architecture
    // 1 Dockerfile, a set of instructions for building an image when "docker build" is called
    // 2 Docker-compose, a yaml file that holds instructions, for building multi container application

    //todo G
    // Name and explain why are companies slow in moving existing systems to docker architecture (do not explain why docker is bad)
    // 1 it will take a lot of resources to move to docker
    // 2 docker might not be the preffered solution given the company tech stack
}

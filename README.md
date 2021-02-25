<!--<html>
<head>
  <style>
    details > summary {
      background-color: #444444;
      color: #c71585;
    }
    h1 {
      background-color: #444444;
      color: #ffff00;
    }
  </style>
</head> -->
<body>
  
<!-- project title -->
<div align="center">
<h1>Eight puzzle A.I.</h1>
<p>
  solving eight puzzle using BFS, DFS, A*, IDA*
</p>
</div>
  
<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#eight-puzzle-aka-sliding-puzzle">Eight Puzzle AKA. Sliding Puzzle</a>
      <ul>
        <li><a href="#initial-state">Initial State</a></li>
        <li><a href="#goal-state-game-condition">Goal State (Game Condition)</a></li>
        <li><a href="#actuators-how-to-play">Actuators (How to PLAY!)</a></li>
      </ul>
    </li>
    <li>
      <a href="#build-and-run">Build and Run</a>
    </li>
    <li>
      <a href="#references">References</a>
      <ul>
        <li><a href="#about-the-project">about the Project</a></li>
        <li><a href="#about-the-readme">about the README</a></li>
      </ul>
    </li>
  </ol>
</details>

<details open="open">
  
<!-- Eight Puzzle AKA. Sliding Puzzle -->
  <summary>
  
  ## Eight Puzzle AKA. Sliding Puzzle
  </summary>
  
  <!-- what is eight puzzle? -->
  <details open="open">
    <summary><h3>what is eight puzzle though?</h3></summary>
    <br/>
    <p align="center">
      <img src="https://studio.cults3d.com/2Fn4F4daXOL1DKk2l8wkWgHsafE=/246x246/https://files.cults3d.com/uploaders/13634459/illustration-file/f2a3c195-bea9-4f1a-907f-f01974e14b17/Slider%20puzzle07.jpg"></img>
    </p>
    It is a 
    <em>
    static,
    descrete,
    determinant,
    fully observable,
    sequential,
    known</em>
    and
    <em>
    single-agent
    </em>
    environment.
  </details>
    
  <!-- initial state: -->
  <details open="open">
    <summary>

  ### Initial State
    
  </summary>
    A random order of numbers like the table below:
    <br/>
    <br/>
    <!--
    1|3|7
    -|-|-
    2|6|8
    5| |4
    -->
   <table align="center">
      <tr>
        <td>1</td>
        <td>3</td>
        <td>7</td>
      </tr>
      <tr>
        <td>2</td>
        <td>6</td>
        <td>8</td>
      </tr>
      <tr>
        <td>5</td>
        <td> </td>
        <td>4</td>
      </tr>
    </table>

  </details>
  
  <!-- goal state: -->
  <details open="open">
  <summary>
      
  ### Goal State __(Game Condition)__

  </summary>
  The board with an ascending order sequence from top-bottom and left-right. like the table below for a 3*3 board:
  
  
  <br/>
  <br/>
  <table align="center">
    <tr>
      <td>1</td>
      <td>2</td>
      <td>3</td>
    </tr>
    <tr>
      <td>4</td>
      <td>5</td>
      <td>6</td>
    </tr>
    <tr>
      <td>7</td>
      <td>8</td>
      <td> </td>
    </tr>
  </table>
  </details>
  <br/>
  <br/>
  <p align="center">
    <img src="https://media.cheggcdn.com/media/a7d/a7d96cee-5e7a-45b6-b77e-7abda450cab4/phpGUTM6t"></img>
  </p>

  <!-- Actuators (how to play!)-->
  <details open="open">
  <summary>
  
  ### Actuators __(How to PLAY!)__
  </summary>
  you can move the blank cell <em><strong>UP, DOWN, LEFT</strong></em> and <em><strong>RIGHT</strong></em> if it doesn't exceed the limit of the board.

  <p align="center">
    <img src="https://www.cs.princeton.edu/courses/archive/spring20/cos226/assignments/8puzzle/images/4moves.png"></img>
  </p>
  </details>
</details>

<details open="open">
  <summary>
    
  ## Build and Run
  </summary>
</details>

<details open="open">
  <summary>
    
  ## References
  </summary>
  <p>as for this readme there were a lot of useful websites which helped me a lot. (also mentioned people's awesome readmes for reference.)</p>
  
  ### about the Project

1.
      <a href="https://www.javatpoint.com/agent-environment-in-ai">Agent Environments</a>

1.
     <a href="https://github.com/monjar/">@monjar</a>'s (my __TA__ in AI course) awesome projects </a>
        <ul>
          <li>
            <a href="https://github.com/monjar/eight-puzzle-ai-server">Server</a>
          </li>
          <li>
            <a href="https://github.com/monjar/eight-puzzle-ai-client">Client</a>
          </li>
        </ul>


1.
      <a href="https://www.geeksforgeeks.org/a-search-algorithm/">more about A* algorithm</a>

1. 
      <a href="https://en.wikipedia.org/wiki/Iterative_deepening_A*">more about IDA*</a>
      
1. 
      <a href="https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/">more about BFS</a>

1. 
      <a href="https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/">more about DFS</a>
      


### about the README

1.
      <a href="https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/about-readmes">about READMEs</a>

1.
      <a href="https://stackoverflow.com/questions/27174946/how-to-use-css-in-markdown">CSS in markdowns</a>
      
1.
      <a href="https://guides.github.com/features/mastering-markdown/">Mastering Markdown</a>
      
1.
      <a href="https://manifoldapp.org/docs/projects/preparing/md">more about Markdowns</a>

1.
      <a href="https://www.w3schools.com/tags/tag_details.asp">More about HTML &lt;details&gt; tags</a>
      
1. 
      <a href="https://www.w3schools.com/html/html_tables.asp">HTML tables</a>
      
1.
      <a href="https://stackoverflow.com/questions/14051715/markdown-native-text-alignment">Markdown's native text alignment</a>

1.
      <a href="https://github.com/1995parham">@1995parham</a>'s (my __intsructor/professor__ in IE course) awesome <a href="https://raw.githubusercontent.com/1995parham/1995parham/main/README.md">README.md</a>

1.
      <a href="https://github.com/rsharifnasab">@rsharifnasab</a>'s (my __TA__ in IE course) awesome README.md s.

1.
      <a href="https://github.com/othneildrew">@othneildrew</a>'s awesome <a href="https://raw.githubusercontent.com/othneildrew/Best-README-Template/master/README.md">README.md</a> template.
      

      
</details>
</body>
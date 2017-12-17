# Port-Simulation
Handling the docking and undocking of ships on the port.

Port Simulation Description
A port has 2 docks. Each dock has 2 rows of spaces (berths) for ships. The port can
accommodate three sizes of ship: cargo, container, and super-container.
1. The rows are made up of 2 small, 2 medium, and 1 large spaces
2. A cargo ship (small) can berth in any available space
3. A container ship (medium) can berth in either a medium or large space, but not in small spaces
4. A super-container (large) can only berth in large slots. Small and medium spaces cannot be
used for super-containers

The implementation should present a menu allowing users to dock (add) ships in the port,
undock (remove) ships from the port, and report on the status of the port at any point in time.
The dock option will allow the user to enter the ship details (name, size) and then allocate it to
the first available space. The undock option will allow the user to specify a ship based on its
name and then remove it from the list of docked ships. Your algorithm to place ships should be
able to reuse spaces that have become vacated.

If a ship cannot be accommodated in the port, it should be added to a waiting list until an
available space is found. There are five spaces available for ships to wait outside of the port. If
all of the waiting spaces are occupied and another ship arrives at the port, it will be turned away
(what they do next is not your problem!). Once a suitable space is vacated by another ship
undocking, the first ship in the waiting list that can fit, according to the rules above, will be
allocated the space. The option to report on the status of the port should provide a full overview
of all ships docked in the port and on the waiting list at any time.

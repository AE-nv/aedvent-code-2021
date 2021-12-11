import time
from itertools import chain
import pygame
import day11 as octo

octo_colors = list(chain([(238, 157, 41)], [(i, i, i) for i in range(50, 255, 25)], [(255, 255, 255)]))


class OctoPus:

    def __init__(self, position, energy, game):
        self.screen = game.screen
        self.screen_rect = game.screen.get_rect()
        self.image = pygame.image.load('octo.png')
        self.rect = self.image.get_rect()
        self.energy = energy
        x, y = position
        self.rect.x = 20 + x * 70
        self.rect.y = 20 + y * 70

    def draw(self):
        self.screen.fill(octo_colors[self.energy], self.rect)
        self.screen.blit(self.image, self.rect)
        pygame.display.update(self.rect)


class Label:

    def __init__(self, prefix, game, top=35):
        self.step = 0
        self.game = game
        self.screen = game.screen
        self.screen_rect = game.screen.get_rect()
        self.text_color = (238, 157, 41)
        self.font = pygame.font.SysFont(None, 30)
        self.prefix = prefix
        self.image = self.font.render(prefix, True, self.text_color, (0, 0, 0))
        self.rect = self.image.get_rect()
        self.rect.left = 770
        self.rect.top = top

    def draw(self, value):
        self.image = self.font.render(self.prefix + str(value), True, self.text_color, (0, 0, 0))
        self.screen.blit(self.image, self.rect)
        pygame.display.update(self.rect)


class FlashingOctos:

    def __init__(self):
        pygame.init()
        self.screen = pygame.display.set_mode((1344, 756))
        pygame.display.set_caption("Flashing OctoPussies")
        self.octos = {(x, y): OctoPus((x, y), 1, self) for x in range(10) for y in range(10)}
        self.step_label = Label("Step: ", self)
        self.flash_label = Label("Flashes: ", self, top=80)

    def run(self, frames):
        self.screen.fill((0, 0, 0))
        pygame.display.flip()
        pygame.event.get()
        current_step = 0
        for octopy, count, step in frames:
            if step != current_step:
                current_step = step
                for pos, energy in octopy.items():
                    self.octos[pos].energy = energy
                    self.octos[pos].draw()
                self.step_label.draw(step)
            else:
                for pos, energy in octopy.items():
                    if self.octos[pos].energy != energy:
                        self.octos[pos].energy = energy
                        self.octos[pos].draw()
                        time.sleep(0.01)
                self.flash_label.draw(count)
            self.flash_label.draw(count)


FlashingOctos().run(chain(octo.frames))

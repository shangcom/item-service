package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items") // 요청 경로. 절대 경로처럼 작용. 이 클래스의 모든 메서드가 기본으로 사용.
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items"; // 뷰 경로. 상대 경로로 해석. 뷰 리졸버가 이 경로를 기준으로 실제 뷰 파일을 찾음.
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * 수동 매핑. 모델 객체에 수동으로 item을 추가.
     * 컨트롤러 메서드가 실행될 때, 스프링은 필요 시 모델을 자동으로 생성한다.
     * 여기서는 매개변수로 Model을 사용하고 있으므로 자동으로 생성.
     */
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, @RequestParam int price,
                            @RequestParam Integer quantity, Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * '@ReqeustParam' 생략하면 단순 데이터 타입의 경우 자동으로 @RequestParam이 적용되어 바인딩함.
     */
//    @PostMapping("/add")
    public String addItemV2(String itemName, int price, Integer quantity, Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * '@ModelAttribute' 기능
     * 1.요청 파라미터 처리 : 요청 파라미터(itemName, price, quantity)가 자동으로 Item item 객체의 필드에 바인딩됨.
     * 2.모델에 key-value 자동 추가 : 해당 객체는 자동으로 모델에 추가되어 뷰에서 사용할 수 있음.
     * key는 @ModelAttribute("item")의 "item"이 되며, 값은 Item item임.
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * 따라서 위의 메서드에서, 메서드 바디에서 model을 이용해 직접 item을 등록할 필요 없음.
     * 따라서 매개변수에 Model model도 필요 없음.
     */
//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * ("...") 생략한 경우, 모델에 담길 객체의 클래스 명의 첫 글자를 소문자로 바꾼 것으로 자동 등록됨.
     * 여기서는 Item item 이므로, Item의 첫 글자를 소문자로 바꾼 "item"을 key로 자동 등록됨.
     */
//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * '@ModelAttribute'도 생략 가능.
     * 메서드 파라미터에 단순 데이터 타입(String, int 등)이 아닌 사용자 정의 객체(Item 등)가 있으면,
     * 스프링은 이를 모델로 간주하고 자동으로 바인딩 및 모델에 추가.
     * 만약 메서드 파라미터에 객체 타입이 명시되어 있으면, 이를 생략해도 @ModelAttribute와 동일한 동작을 함.
     * 단순 데이터 타입은 @RequestParam으로 처리됨.
     */
    @PostMapping("/add")
    public String addItemV6(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }


}
